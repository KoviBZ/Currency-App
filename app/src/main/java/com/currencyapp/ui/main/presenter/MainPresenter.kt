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

    //no progress bar, as with 1 second interval it would be bad UX
    fun retrieveCurrencyResponse(currency: String = Constants.DEFAULT_CURRENCY) {
        val disposable = model.retrieveCurrencyResponse(currency)
            .subscribeOnIo()
            .repeatWhen { completed -> completed.delay(Constants.REQUEST_INTERVAL, TimeUnit.SECONDS) }
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

    //progress bar, as request usually takes more than REQUEST_INTERVAL.
    fun retry() {
        val currency = model.getBaseCurrency()

        view.showProgress()

        val disposable = model.retrieveCurrencyResponse(currency)
            .subscribeOnIo()
            .repeatWhen { completed -> completed.delay(Constants.REQUEST_INTERVAL, TimeUnit.SECONDS) }
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