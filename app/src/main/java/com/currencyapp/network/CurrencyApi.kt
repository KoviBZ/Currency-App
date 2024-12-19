package com.currencyapp.network

import com.currencyapp.network.entity.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("latest")
    fun getCurrencies(@Query("base") base: String): Response<CurrencyResponse>

}