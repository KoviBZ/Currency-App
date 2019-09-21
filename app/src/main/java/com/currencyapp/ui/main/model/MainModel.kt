package com.currencyapp.ui.main.model

import com.currencyapp.dto.CurrencyResponse
import com.currencyapp.network.CurrencyApi
import io.reactivex.Single

class MainModel(
    private val currencyApi: CurrencyApi
) {

    fun retrieveCurrencyResponse(baseCurrency: String): Single<CurrencyResponse> {
        return currencyApi.getCurrencies(baseCurrency)
    }
}