package com.currencyapp.network.repo

import com.currencyapp.network.entity.RateDto

interface RemoteRepository {
    fun getBaseCurrency(): String
    fun getCurrencyResponse(currencyName: String): Resource<List<RateDto>>
}