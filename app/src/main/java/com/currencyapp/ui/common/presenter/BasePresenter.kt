package com.currencyapp.ui.common.presenter

import io.reactivex.disposables.CompositeDisposable

open class BasePresenter<BaseView> {

    private var view: BaseView? = null

    private lateinit var subscriptions: CompositeDisposable

    fun onAttach(view: BaseView) {
        this.view = view
        subscriptions = CompositeDisposable()
    }

    fun onDetach() {
        subscriptions.clear()
    }
}