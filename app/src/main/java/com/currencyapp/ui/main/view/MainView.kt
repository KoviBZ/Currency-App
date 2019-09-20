package com.currencyapp.ui.main.view

import com.currencyapp.dto.CurrencyResponse
import com.currencyapp.ui.common.view.BaseView

interface MainView: BaseView {

    fun onDataLoadedSuccess(currencyList: List<CurrencyResponse>)

    fun onDataLoadedFailure(error: Throwable)

    fun onCashFieldClicked()

    fun onCashFieldChanged()
}