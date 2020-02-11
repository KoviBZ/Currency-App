package com.currencyapp.ui.main.presenter

import com.currencyapp.network.entity.RateDto
import com.currencyapp.network.utils.BaseSchedulerProvider
import com.currencyapp.ui.common.presenter.BasePresenter
import com.currencyapp.ui.main.model.MainModel
import com.currencyapp.ui.main.view.MainView
import com.currencyapp.utils.Constants
import java.util.concurrent.TimeUnit

class MainPresenter(
    schedulerProvider: BaseSchedulerProvider,
    private val model: MainModel
) : BasePresenter<MainView>(schedulerProvider) {

    private var baseCurrency = ""

    fun retrieveCurrencyResponse(currency: String) {
        this.baseCurrency = currency

        val disposable = model.retrieveCurrencyResponse(currency)
            .delay(Constants.REQUEST_INTERVAL, TimeUnit.SECONDS)
            .applySchedulers()
            .repeatUntil {
                currency != baseCurrency
            }
            .subscribe(
                { response ->
                    view.onDataLoadedSuccess(response)
                },
                { throwable ->
                    view.onDataLoadedFailure(throwable)
                }
            )

        subscriptions.add(disposable)
    }

    fun onTextChanged(changedMultiplier: Double) {
        view.updateRates(changedMultiplier)
    }

    fun onItemMoved(itemOnTop: RateDto) {
        baseCurrency = itemOnTop.key
        subscriptions.clear()

        retrieveCurrencyResponse(itemOnTop.key)
    }

    fun restartSubscription() {
        if (subscriptions.size() == 0) {
            retrieveCurrencyResponse(baseCurrency)
        }
    }

    fun retry() {
        val currency = baseCurrency

        val disposable = model.retrieveCurrencyResponse(currency)
            .delay(1, TimeUnit.SECONDS)
            .applySchedulers()
            .applySubscribeActions()
            .repeatUntil {
                val isGood = currency != baseCurrency
                isGood
            }
            .subscribe(
                { response ->
                    view.onDataLoadedSuccess(response)
                },
                { throwable ->
                    view.onDataLoadedFailure(throwable)
                }
            )

        subscriptions.add(disposable)
    }
}