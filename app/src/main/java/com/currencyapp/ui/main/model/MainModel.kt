package com.currencyapp.ui.main.model

import com.currencyapp.network.entity.RateDto
import io.reactivex.Completable
import io.reactivex.Single

interface MainModel {
    fun retrieveCurrencyResponse(currency: String): Single<List<RateDto>>

    fun getBaseCurrency(): String

    fun saveDataForOfflineMode(listToStore: List<RateDto>): Completable
    fun getOfflineData(): Single<List<RateDto>>
}