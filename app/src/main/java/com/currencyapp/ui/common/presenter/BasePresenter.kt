package com.currencyapp.ui.common.presenter

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

open class BasePresenter<BaseView> {

    protected var view: BaseView? = null

    protected lateinit var subscriptions: CompositeDisposable

    fun attachView(view: BaseView) {
        this.view = view
        subscriptions = CompositeDisposable()
    }

    fun detachView() {
        subscriptions.clear()
    }

    fun <RESPONSE> Single<RESPONSE>.applySchedulers(
        scheduler: Scheduler
    ): Single<RESPONSE> {
        return this
            .subscribeOn(scheduler)
            .observeOn(AndroidSchedulers.mainThread())

        //subscriptions.add(disposable)
    }

    fun clearSubscriptions() {
        subscriptions.clear()
    }

    fun disposeSubscriptions() {
        subscriptions.dispose()
    }
}