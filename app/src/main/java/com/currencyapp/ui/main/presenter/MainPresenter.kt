package com.currencyapp.ui.main.presenter

import com.currencyapp.dto.RateDto
import com.currencyapp.ui.common.presenter.BasePresenter
import com.currencyapp.ui.main.model.MainModel
import com.currencyapp.ui.main.view.MainView
import com.currencyapp.utils.mapper.Mapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.schedulers.IoScheduler

class MainPresenter(
    private val model: MainModel,
    private val mapper: Mapper<Map.Entry<String, Double>, RateDto>
) : BasePresenter<MainView>() {

    fun retrieveCurrencyResponse(baseCurrency: String) {
        model.retrieveCurrencyResponse(baseCurrency)
            .subscribeOn(IoScheduler())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { response ->
                val list = ArrayList<RateDto>()

                response.rates.iterator().forEach {
                    list.add(mapper.map(it))
                }

                view?.onDataLoadedSuccess(list)
            }
            .doOnError { throwable ->
                view?.onDataLoadedFailure(throwable)
            }
            .subscribe()
    }
}