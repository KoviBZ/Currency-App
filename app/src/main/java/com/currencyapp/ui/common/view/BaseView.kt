package com.currencyapp.ui.common.view

interface BaseView<BasePresenter> {

    val presenter: BasePresenter

    fun showProgress()

    fun hideProgress()
}