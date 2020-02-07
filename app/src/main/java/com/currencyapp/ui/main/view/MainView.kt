package com.currencyapp.ui.main.view

import com.currencyapp.network.entity.RateDto
import com.currencyapp.ui.common.view.BaseView

interface MainView: BaseView {

    fun onDataLoadedSuccess(currencyList: List<RateDto>)

    fun onDataLoadedFailure(error: Throwable)

    fun updateRates(changedMultiplier: Double)
}