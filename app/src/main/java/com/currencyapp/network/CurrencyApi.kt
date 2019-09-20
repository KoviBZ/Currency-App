package com.currencyapp.network

import com.currencyapp.dto.CurrencyResponse
import retrofit2.Call
import retrofit2.http.GET

interface CurrencyApi {

    @GET("/files/bought_cds.json")
    fun getCurrencies(): Call<CurrencyResponse>
}