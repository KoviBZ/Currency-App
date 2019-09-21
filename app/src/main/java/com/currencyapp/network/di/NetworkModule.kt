package com.currencyapp.network.di

import com.currencyapp.network.CurrencyApi
import dagger.Module
import dagger.Provides
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import okhttp3.OkHttpClient

@Module
object NetworkModule {

    val HOST = "https://revolut.duckdns.org/"

    @Provides
    fun provideCurrencyApi(): CurrencyApi {
        val httpClient = OkHttpClient.Builder()

        val retrofit = Retrofit.Builder()
            .baseUrl(HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()

        return retrofit.create(CurrencyApi::class.java)
    }
}