package com.currencyapp.ui.common.presenter

import io.reactivex.disposables.CompositeDisposable

open class BasePresenter<BaseView> {

    protected var view: BaseView? = null

    private lateinit var subscriptions: CompositeDisposable

    fun attachView(view: BaseView) {
        this.view = view
        subscriptions = CompositeDisposable()
    }

    fun detachView() {
        subscriptions.clear()
    }

//    protected fun executeOn(
//        scheduler: Scheduler,
//        observable: Observable<RESPONSE>,
//        onNextAction: Consumer<in RESPONSE>,
//        onErrorAction: Consumer<in Throwable>,
//        onCompleteAction: Action
//    ) {
//        observable
//            .subscribeOn(scheduler)
//            .doOnNext(onNextAction)
//            .doOnComplete(onCompleteAction)
//            .doOnError(onErrorAction)
//            .subscribe()
//    }
//
//    protected fun executeOn(
//        scheduler: Scheduler,
//        single: Single<RESPONSE>,
//        onSuccessAction: Consumer<in RESPONSE>,
//        onErrorAction: Consumer<in Throwable>
//    ) {
//        single
//            .subscribeOn(scheduler)
//            .doOnSuccess(onSuccessAction)
//            .doOnError(onErrorAction)
//            .subscribe()
//    }
//
//    protected fun executeOn(
//        scheduler: Scheduler,
//        completable: Completable,
//        onCompleteAction: Action,
//        onErrorAction: Consumer<Throwable>
//    ) {
//        completable
//            .subscribeOn(scheduler)
//            .doOnComplete(onCompleteAction)
//            .doOnError(onErrorAction)
//            .subscribe()
//    }
}