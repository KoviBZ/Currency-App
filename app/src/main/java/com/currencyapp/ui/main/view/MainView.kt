package com.currencyapp.ui.main.view

import com.currencyapp.localrepo.RateDto
import com.currencyapp.ui.common.view.BaseView

interface MainView: BaseView {

    fun onDataLoadedSuccess(currencyList: ArrayList<RateDto>)

    fun onDataLoadedFailure(error: Throwable)

    fun updateRates(changedMultiplier: Double)
}