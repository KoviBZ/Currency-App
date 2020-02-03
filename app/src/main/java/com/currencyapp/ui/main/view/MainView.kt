package com.currencyapp.ui.main.view

import com.currencyapp.localrepo.RateDto
import com.currencyapp.ui.common.view.BaseView
import com.currencyapp.ui.main.presenter.MainPresenter

interface MainView: BaseView<MainPresenter> {

    fun onDataLoadedSuccess(currencyList: ArrayList<RateDto>)

    fun onDataLoadedFailure(error: Throwable)

    fun onCashFieldClicked()

    fun onCashFieldChanged()

    fun updateRates(changedMultiplier: Double)
}