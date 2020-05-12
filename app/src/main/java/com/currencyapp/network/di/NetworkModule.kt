package com.currencyapp.network.di

import com.currencyapp.BuildConfig
import com.currencyapp.network.CurrencyApi
import com.currencyapp.network.entity.RateDto
import com.currencyapp.network.mapper.RatesToRateDtosMapper
import com.currencyapp.network.repo.ApiRepository
import com.currencyapp.network.repo.RemoteRepository
import com.currencyapp.utils.Constants
import com.currencyapp.utils.mapper.Mapper
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
            .connectTimeout(Constants.TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constants.TIMEOUT, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .build()

        return retrofit.create(CurrencyApi::class.java)
    }

    @Provides
    fun provideRemoteRepository(
        currencyApi: CurrencyApi,
        mapper: Mapper<Map.Entry<String, Double>, RateDto>
    ): RemoteRepository {
        return ApiRepository(currencyApi, mapper)
    }

    @Provides
    fun provideRemoteMapper(): Mapper<Map.Entry<String, Double>, RateDto> =
        RatesToRateDtosMapper()
}