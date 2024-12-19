package com.currencyapp.ui.main.presenter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currencyapp.network.entity.RateDto
import com.currencyapp.network.repo.RemoteRepository
import com.currencyapp.network.repo.Resource
import com.currencyapp.utils.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: RemoteRepository
): ViewModel() {

    var currencyUiState: CurrencyUiState by mutableStateOf(CurrencyUiState.Loading)
        private set

    //no progress bar, as with 1 second interval it would be bad UX
    fun retrieveCurrencyResponse(currency: String = Constants.DEFAULT_CURRENCY) {
        viewModelScope.launch {
            repeat(20) {
                val response = repository.getCurrencyResponse(currency)

                currencyUiState = if (response is Resource.Success) {
                    CurrencyUiState.Success(response.data)
                } else {
                    CurrencyUiState.Error
                }
                delay(1000L)
            }
        }
    }

    fun onTextChanged(changedMultiplier: Double) {
//        view.updateRates(changedMultiplier)
    }

    fun onItemMoved(itemOnTop: RateDto) {
//        subscriptions.clear()

        retrieveCurrencyResponse(itemOnTop.key)
    }

    fun restartSubscription() {
//        if (subscriptions.size() == 0) {
            retrieveCurrencyResponse(repository.getBaseCurrency())
//        }
    }

    //progress bar, as request usually takes more than REQUEST_INTERVAL.
    fun retry() {
        val currency = repository.getBaseCurrency()

        currencyUiState = CurrencyUiState.Loading

//        val disposable = repository.getCurrencyResponse(currency)
//            .applyDefaultIOSchedulers()
//            .repeatWhen { completed ->
//                completed.delay(
//                    Constants.REQUEST_INTERVAL,
//                    TimeUnit.SECONDS
//                )
//            }
//            .subscribe(
//                { response ->
//                    currencyUiState = CurrencyUiState.Success(response)
//                },
//                { throwable ->
//                    currencyUiState = CurrencyUiState.Error
//                }
//            )
//
//        subscriptions.add(disposable)
    }
}

sealed interface CurrencyUiState {
    data class Success(val currencies: List<RateDto>) : CurrencyUiState
    object Error : CurrencyUiState
    object Loading : CurrencyUiState
}