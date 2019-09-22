package com.currencyapp.ui.main.view

import com.currencyapp.dto.RateDto
import com.currencyapp.ui.common.view.BaseView

interface MainView: BaseView {

    fun onDataLoadedSuccess(currencyList: List<RateDto>)

    fun onDataLoadedFailure(error: Throwable)

    fun onCashFieldClicked()

    fun onCashFieldChanged()
}