package com.currencyapp.ui.main.presenter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currencyapp.network.entity.RateDto
import com.currencyapp.network.repo.RemoteRepository
import com.currencyapp.network.repo.Resource
import com.currencyapp.utils.Constants
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: RemoteRepository
): ViewModel() {

    private var job: Job? = null

    var currencyUiState: MutableStateFlow<CurrencyUiState> = MutableStateFlow(CurrencyUiState.Loading)
        private set
    var modifierUiState: MutableStateFlow<Double> = MutableStateFlow(1.0)
        private set

    init {
        Log.d("ViewModel", "MainViewModel lives")
    }

    //TODO retry spams error value - fix it!
    //no progress bar, as with 1 second interval it would be bad UX
    private fun retrieveCurrencyResponse(currency: String = Constants.DEFAULT_CURRENCY) {
        job = viewModelScope.launch {
            while (true) {
                ensureActive()
                try {
                    val response = repository.getCurrencyResponse(currency)

                    currencyUiState.value = if (response is Resource.Success) {
                        CurrencyUiState.Success(response.data)
                    } else {
                        CurrencyUiState.Error
                    }
                    delay(1000L)
                } catch (ex: Exception) {
                    Log.d("testo", "error, possibly no internet")
                    currencyUiState.value = CurrencyUiState.Error
                }
            }
        }
    }

    fun onTextChanged(changedMultiplier: Double) {
        modifierUiState.value = changedMultiplier
    }

    fun onItemMoved(itemOnTop: RateDto) {
        job?.cancel()

        retrieveCurrencyResponse(itemOnTop.key)
    }

    fun restartSubscription() {
        Log.d("testo", "restarting...")
        if (job == null || job?.isActive != true) {
            retrieveCurrencyResponse(repository.getBaseCurrency())
        }
    }

    fun clearSubscriptions() {
        viewModelScope.launch {
            job?.cancelAndJoin()
        }
    }
}

sealed interface CurrencyUiState {
    data class Success(val currencies: List<RateDto>) : CurrencyUiState
    object Error : CurrencyUiState
    object Loading : CurrencyUiState
}