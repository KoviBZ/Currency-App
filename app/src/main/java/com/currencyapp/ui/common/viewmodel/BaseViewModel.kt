package com.currencyapp.ui.common.viewmodel

import androidx.annotation.VisibleForTesting
import com.currencyapp.network.utils.BaseSchedulerProvider
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel(
    private val schedulerProvider: BaseSchedulerProvider
) {

    @VisibleForTesting
    var subscriptions: CompositeDisposable = CompositeDisposable()

    fun detachView() {
        subscriptions.dispose()
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
}