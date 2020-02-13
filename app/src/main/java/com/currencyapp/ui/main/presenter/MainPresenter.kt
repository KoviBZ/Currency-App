package com.currencyapp.ui.main.presenter

import com.currencyapp.network.entity.RateDto
import com.currencyapp.network.utils.BaseSchedulerProvider
import com.currencyapp.ui.common.presenter.BasePresenter
import com.currencyapp.ui.main.model.MainModel
import com.currencyapp.ui.main.view.MainView
import com.currencyapp.utils.Constants
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class MainPresenter(
    schedulerProvider: BaseSchedulerProvider,
    private val model: MainModel
) : BasePresenter<MainView>(schedulerProvider) {

    fun retrieveCurrencyResponse(currency: String = Constants.DEFAULT_CURRENCY) {
        val disposable = model.retrieveCurrencyResponse(currency)
            .applySchedulers()
            .repeatWhen { completed -> completed.delay(Constants.REQUEST_INTERVAL, TimeUnit.SECONDS) }
            .repeatUntil { currency != model.getBaseCurrency() }
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
        subscriptions.clear()

        retrieveCurrencyResponse(itemOnTop.key)
    }

    fun restartSubscription() {
        if (subscriptions.size() == 0) {
            retrieveCurrencyResponse(model.getBaseCurrency())
        }
    }

    fun retry() {
        val currency = model.getBaseCurrency()

        view.showProgress()

        val disposable = model.retrieveCurrencyResponse(currency)
            .applySchedulers()
            .repeatWhen { completed -> completed.delay(Constants.REQUEST_INTERVAL, TimeUnit.SECONDS) }
            .repeatUntil { currency != model.getBaseCurrency() }
            .subscribe(
                { response ->
                    view.onDataLoadedSuccess(response)
                    view.hideProgress()
                },
                { throwable ->
                    view.onDataLoadedFailure(throwable)
                    view.hideProgress()
                }
            )

        subscriptions.add(disposable)
    }
}