package com.currencyapp.ui.main.presenter

import com.currencyapp.localrepo.RateDto
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
        val disposable = model.retrieveCurrencyResponse(baseCurrency)
            .subscribeOn(IoScheduler())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { response ->
                val list = ArrayList<RateDto>()

                list.add(RateDto(baseCurrency, 100.00))

                response.rates.iterator().forEach {
                    list.add(mapper.map(it))
                }

                view?.onDataLoadedSuccess(list)
                view?.hideProgress()
            }
            .doOnError { throwable ->
                view?.onDataLoadedFailure(throwable)
                view?.hideProgress()
            }
            .doOnSubscribe { view?.showProgress() }
//            .doOnUnsubscribe { view?.hideProgress() }
//            .delay(5, TimeUnit.SECONDS)
//            .repeat()
            .subscribe()

        subscriptions.add(disposable)
    }
}