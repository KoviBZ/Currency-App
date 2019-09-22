package com.currencyapp.network

import com.currencyapp.dto.CurrencyResponse
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("latest")
    fun getCurrencies(
        @Query("base") base: String
    ): Single<CurrencyResponse>

    @GET("latest")
    fun callable(
        @Query("base") base: String
    ): Single<Response<ResponseBody>>
}