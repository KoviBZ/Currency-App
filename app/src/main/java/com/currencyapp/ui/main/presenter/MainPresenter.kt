package com.currencyapp.ui.main.presenter

import com.currencyapp.localrepo.CurrencyResponse
import com.currencyapp.localrepo.RateDto
import com.currencyapp.ui.common.presenter.BasePresenter
import com.currencyapp.ui.main.model.MainModel
import com.currencyapp.ui.main.view.MainView
import com.currencyapp.utils.mapper.Mapper
import io.reactivex.internal.schedulers.IoScheduler
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.concurrent.TimeUnit

class MainPresenter(
    private val model: MainModel,
    private val mapper: Mapper<Map.Entry<String, Double>, RateDto>
) : BasePresenter<MainView>() {

    fun retrieveCurrencyResponse(baseCurrency: String) {
        val disposable =
            model.retrieveCurrencyResponse(baseCurrency)
                .applySchedulers(IoScheduler())
                .doOnSuccess { response ->
                    model.tempSaveResponse(response)
                    successAction(response, baseCurrency)
                }
                .doOnError { throwable ->
                    failureAction(throwable)
                }
//                .delay(2, TimeUnit.SECONDS)
//                .repeat()
                .subscribe()

        subscriptions.add(disposable)
    }

    fun onTextChanged(afterChangeText: String) {
        model.currentMultiplier = DecimalFormat.getInstance().parse(afterChangeText).toDouble()

        successAction(model.tempGetResponse(), "EUR", model.currentMultiplier)
    }

    private fun successAction(response: CurrencyResponse, baseCurrency: String, multiplier: Double = 100.00) {
        val list = ArrayList<RateDto>()

        list.add(RateDto(baseCurrency, multiplier))

        response.rates.iterator().forEach {
            list.add(mapper.mapWithMultiplier(it, multiplier))
        }

        view?.onDataLoadedSuccess(list)
        view?.hideProgress()
    }

    private fun failureAction(throwable: Throwable) {
        view?.onDataLoadedFailure(throwable)
        view?.hideProgress()
    }
}