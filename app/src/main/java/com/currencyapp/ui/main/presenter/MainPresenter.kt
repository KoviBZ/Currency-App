package com.currencyapp.ui.main.presenter

import com.currencyapp.localrepo.RateDto
import com.currencyapp.ui.common.presenter.BasePresenter
import com.currencyapp.ui.main.model.MainModel
import com.currencyapp.ui.main.view.MainView
import io.reactivex.internal.schedulers.IoScheduler
import java.text.DecimalFormat
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainPresenter(private var model: MainModel) :
    BasePresenter<MainView>() {

    fun retrieveCurrencyResponse() {
        val disposable =
            model.retrieveCurrencyResponse()
                .applySchedulers(IoScheduler())
                .doOnSuccess { response ->
                    successAction(response)
                }
                .doOnError { throwable ->
                    failureAction(throwable)
                }
                .delay(2, TimeUnit.SECONDS)
//                .repeat()
                .subscribe()

        subscriptions.add(disposable)
    }

    fun onTextChanged(afterChangeText: String) {
        val multiplier = DecimalFormat.getInstance().parse(afterChangeText).toDouble()
        model.tmpMultiplier = multiplier

        model.retrieveCurrencyResponse()
    }

    fun onFieldClicked(rateDto: RateDto) {
        model.tempSaveBaseCurrency(rateDto.key)
        model.tmpMultiplier = rateDto.value
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