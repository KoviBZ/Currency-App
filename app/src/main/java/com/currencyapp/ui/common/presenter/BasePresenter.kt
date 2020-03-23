package com.currencyapp.ui.common.presenter

import androidx.annotation.VisibleForTesting
import com.currencyapp.network.utils.BaseSchedulerProvider
import com.currencyapp.ui.common.view.BaseView
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

open class BasePresenter<T: BaseView>(
    private val schedulerProvider: BaseSchedulerProvider
) {

    protected lateinit var view: T

    @VisibleForTesting
    lateinit var subscriptions: CompositeDisposable

    fun attachView(view: T) {
        this.view = view
        subscriptions = CompositeDisposable()
    }

    fun detachView() {
        subscriptions.clear()
    }

    fun <RESPONSE> Single<RESPONSE>.applyDefaultIOSchedulers(): Single<RESPONSE> {
        return this
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }

    fun Completable.applyDefaultIOSchedulers(): Completable {
        return this
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }

    fun clearSubscriptions() {
        subscriptions.clear()
    }

    fun disposeSubscriptions() {
        subscriptions.dispose()
    }
}