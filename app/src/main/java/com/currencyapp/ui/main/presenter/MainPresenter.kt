package com.currencyapp.ui.main.presenter

import com.currencyapp.localrepo.RateDto
import com.currencyapp.ui.common.presenter.BasePresenter
import com.currencyapp.ui.main.model.MainModel
import com.currencyapp.ui.main.view.MainView
import io.reactivex.internal.schedulers.IoScheduler

class MainPresenter(private var model: MainModel) : BasePresenter<MainView>() {

    fun retrieveCurrencyResponse(multiplier: Double) {
        val disposable = model.retrieveCurrencyResponse()
            .applySchedulers(IoScheduler())
            .doOnSuccess { response ->
                successAction(response)
            }
            .doOnError { throwable ->
                failureAction(throwable)
            }
//            .delay(2, TimeUnit.SECONDS)
//            .repeat()
            .subscribe()

        subscriptions.add(disposable)
    }

    fun onTextChanged(currency: String, changedMultiplier: Double) {
        if(currency != model.tempGetBaseCurrency()) {
            model.tempSaveBaseCurrency(currency)
            retrieveCurrencyResponse(changedMultiplier)
        } else {
            view?.updateRates(changedMultiplier)
        }
    }

    fun onFieldClicked(rateDto: RateDto) {
        model.tempSaveBaseCurrency(rateDto.key)

        retrieveCurrencyResponse(rateDto.value)
    }

    private fun successAction(response: ArrayList<RateDto>) {
        view?.onDataLoadedSuccess(response)
//        view?.hideProgress()
    }

    private fun failureAction(throwable: Throwable) {
        view?.onDataLoadedFailure(throwable)
//        view?.hideProgress()
    }
}