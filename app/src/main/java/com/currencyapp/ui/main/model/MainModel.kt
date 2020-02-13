package com.currencyapp.ui.main.model

import com.currencyapp.network.entity.RateDto
import io.reactivex.Observable
import io.reactivex.Single

interface MainModel {
    fun retrieveCurrencyResponse(currency: String): Single<List<RateDto>>
//    fun retrieveCurrencyResponse(currency: String): Observable<List<RateDto>>
    fun getBaseCurrency(): String
}