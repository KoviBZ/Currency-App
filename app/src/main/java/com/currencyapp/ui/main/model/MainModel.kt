package com.currencyapp.ui.main.model

import com.currencyapp.network.entity.RateDto
import io.reactivex.Single

interface MainModel {
//    var baseCurrency: String

    fun retrieveCurrencyResponse(currency: String): Single<List<RateDto>>
    fun setBaseCurrency(currency: String)
    fun getBaseCurrency(): String
}