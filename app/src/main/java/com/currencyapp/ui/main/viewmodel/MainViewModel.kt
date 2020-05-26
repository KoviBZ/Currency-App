package com.currencyapp.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import com.currencyapp.network.Resource
import com.currencyapp.network.entity.RateDto
import com.currencyapp.network.utils.BaseSchedulerProvider
import com.currencyapp.ui.common.viewmodel.BaseViewModel
import com.currencyapp.ui.main.model.MainModel
import com.currencyapp.utils.Constants
import com.currencyapp.utils.NoOfflineDataError
import java.util.concurrent.TimeUnit

class MainViewModel(
    schedulerProvider: BaseSchedulerProvider,
    private val model: MainModel
) : BaseViewModel(schedulerProvider) {

    private val currencies = MutableLiveData<Resource<List<RateDto>>>()
    private val multiplier = MutableLiveData<Double>()

    //no progress bar, as with 1 second interval it would be bad UX
    fun retrieveCurrencyResponse(currency: String = Constants.DEFAULT_CURRENCY) {
        val disposable = model.retrieveCurrencyResponse(currency)
            .applyDefaultIOSchedulers()
            .repeatWhen { completed ->
                completed.delay(
                    Constants.REQUEST_INTERVAL,
                    TimeUnit.SECONDS
                )
            }
            .subscribe(
                { response ->
                    currencies.postValue(Resource.success(response))
                },
                { throwable ->
                    currencies.postValue(Resource.error(throwable))
                }
            )

        subscriptions.add(disposable)
    }

    fun onTextChanged(changedMultiplier: Double) {
        multiplier.postValue(changedMultiplier)
    }

    fun onItemMoved(itemOnTop: RateDto) {
        subscriptions.clear()

        retrieveCurrencyResponse(itemOnTop.key)
    }

    fun saveDataForOfflineMode(list: List<RateDto>) {
        val disposable = model.saveDataForOfflineMode(list)
            .applyDefaultIOSchedulers()
            .subscribe()

        subscriptions.add(disposable)
    }

    fun getOfflineData() {
        val disposable = model.getOfflineData()
            .applyDefaultIOSchedulers()
            .subscribe(
                { response ->
                    if (response.isEmpty()) {
                        currencies.postValue(Resource.error(NoOfflineDataError()))
                    } else {
                        currencies.postValue(Resource.success(response))
                    }
                },
                { throwable ->
                    currencies.postValue(Resource.error(throwable))
                }
            )

        subscriptions.add(disposable)
    }

    fun restartSubscription() {
        if (subscriptions.size() == 0) {
            retrieveCurrencyResponse(model.getBaseCurrency())
        }
    }

    //progress bar, as request usually takes more than REQUEST_INTERVAL.
    fun retry() {
        val currency = model.getBaseCurrency()

        Resource.loading(null) // showProgress

        val disposable = model.retrieveCurrencyResponse(currency)
            .applyDefaultIOSchedulers()
            .repeatWhen { completed ->
                completed.delay(
                    Constants.REQUEST_INTERVAL,
                    TimeUnit.SECONDS
                )
            }
            .subscribe(
                { response ->
                    currencies.postValue(Resource.success(response))
                    view.hideProgress()
                },
                { throwable ->
                    currencies.postValue(Resource.error(throwable))
                    view.hideProgress()
                }
            )

        subscriptions.add(disposable)
    }
}