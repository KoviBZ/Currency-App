package com.currencyapp.ui.common.view

import com.currencyapp.ui.common.presenter.BasePresenter

interface BaseView {

    val presenter: BasePresenter<out BaseView>

    fun showProgress()

    fun hideProgress()
}