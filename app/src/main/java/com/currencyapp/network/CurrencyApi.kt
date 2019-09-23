package com.currencyapp.network

import com.currencyapp.localrepo.CurrencyResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("latest")
    fun getCurrencies(
        @Query("base") base: String
    ): Single<CurrencyResponse>

    @GET("latest")
    fun getCurrenciesTemp(
        @Query("base") base: String
    ): Observable<CurrencyResponse>
}