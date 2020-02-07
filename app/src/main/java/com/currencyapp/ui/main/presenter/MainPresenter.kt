package com.currencyapp.ui.main.presenter

import com.currencyapp.network.utils.BaseSchedulerProvider
import com.currencyapp.network.utils.SchedulerProvider
import com.currencyapp.ui.common.presenter.BasePresenter
import com.currencyapp.ui.main.model.MainModel
import com.currencyapp.ui.main.view.MainView
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainPresenter(
    schedulerProvider: BaseSchedulerProvider,
    private val model: MainModel
) : BasePresenter<MainView>(schedulerProvider) {

    private var baseCurrency = ""

    fun retrieveCurrencyResponse(currency: String) {
        this.baseCurrency = currency

        val disposable = model.retrieveCurrencyResponse(currency)
            .applySchedulers()
            .delay(1, TimeUnit.SECONDS)
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

    fun onTextChanged(currency: String, changedMultiplier: Double) {
        if (currency != baseCurrency) {
            retrieveCurrencyResponse(currency)
        } else {
            view.updateRates(changedMultiplier)
        }
    }

    fun restartSubscription() {
        if (subscriptions.size() == 0) {
            retrieveCurrencyResponse(baseCurrency)
        }
    }
}