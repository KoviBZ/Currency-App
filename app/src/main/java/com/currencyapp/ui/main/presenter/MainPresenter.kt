package com.currencyapp.ui.main.presenter

import com.currencyapp.ui.common.presenter.BasePresenter
import com.currencyapp.ui.main.model.MainModel
import com.currencyapp.ui.main.view.MainView
import io.reactivex.internal.schedulers.IoScheduler

class MainPresenter(
    val model: MainModel
) : BasePresenter<MainView>() {

    fun retrieveCurrencyResponse(baseCurrency: String) {
        model.retrieveCurrencyResponse(baseCurrency)
            .subscribeOn(IoScheduler())
//            .doOnSuccess(response ->
//            view.onDataLoadedSuccess(response)
//        )
//        .doOnError(throwable ->
//        view.onDataLoadedFailed(throwable)
//        )
        .subscribe()
    }
}