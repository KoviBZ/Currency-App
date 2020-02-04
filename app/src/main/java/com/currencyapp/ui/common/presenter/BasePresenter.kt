package com.currencyapp.ui.common.presenter

import com.currencyapp.ui.common.view.BaseView
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

open class BasePresenter<T: BaseView> {

    protected lateinit var view: T

    protected lateinit var subscriptions: CompositeDisposable

    fun attachView(view: T) {
        this.view = view
        subscriptions = CompositeDisposable()
    }

    fun detachView() {
        subscriptions.clear()
    }

    fun <RESPONSE> Single<RESPONSE>.applySchedulers(scheduler: Scheduler): Single<RESPONSE> {
        return this
            .subscribeOn(scheduler)
            .observeOn(AndroidSchedulers.mainThread())
//            .doOnSubscribe { view.showProgress() }
    }

    fun clearSubscriptions() {
        subscriptions.clear()
    }

    fun disposeSubscriptions() {
        subscriptions.dispose()
    }
}