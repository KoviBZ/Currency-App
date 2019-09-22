package com.currencyapp.network

import com.currencyapp.localrepo.CurrencyResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("latest")
    fun getCurrencies(
        @Query("base") base: String
    ): Single<CurrencyResponse>
}