package com.currencyapp.network.di

import com.currencyapp.BuildConfig
import com.currencyapp.network.CurrencyApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    private val HOST = BuildConfig.BASE_URL

    @Provides
    fun provideCurrencyApi(): CurrencyApi {
        val httpClient = OkHttpClient.Builder()
        httpClient
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
//            .addInterceptor()

        val retrofit = Retrofit.Builder()
            .baseUrl(HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient.build())
            .build()

        return retrofit.create(CurrencyApi::class.java)
    }
}