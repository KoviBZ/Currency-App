package com.currencyapp.ui.common.presenter

import com.currencyapp.network.utils.BaseSchedulerProvider
import com.currencyapp.ui.common.view.BaseView
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

open class BasePresenter<T: BaseView>(
    private val schedulerProvider: BaseSchedulerProvider
) {

    protected lateinit var view: T

    protected lateinit var subscriptions: CompositeDisposable

    fun attachView(view: T) {
        this.view = view
        subscriptions = CompositeDisposable()
    }

    fun detachView() {
        subscriptions.clear()
    }

    fun <RESPONSE> Observable<RESPONSE>.applySchedulers(): Observable<RESPONSE> {
        return this
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }

    fun <RESPONSE> Single<RESPONSE>.applySchedulers(): Single<RESPONSE> {
        return this
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }

    fun <RESPONSE> Single<RESPONSE>.applySubscribeActions(): Single<RESPONSE> {
        return this
            .doOnSubscribe { view.showProgress() }
            .doFinally { view.hideProgress() }
    }

    fun clearSubscriptions() {
        subscriptions.clear()
    }

    fun disposeSubscriptions() {
        subscriptions.dispose()
    }
}