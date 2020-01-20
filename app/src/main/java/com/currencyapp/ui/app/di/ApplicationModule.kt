package com.currencyapp.ui.app.di

import android.content.Context
import android.content.SharedPreferences
import com.currencyapp.localrepo.RateDto
import com.currencyapp.network.CurrencyApi
import com.currencyapp.ui.main.di.MainComponent
import com.currencyapp.ui.main.model.MainModel
import com.currencyapp.ui.main.presenter.MainPresenter
import com.currencyapp.utils.mapper.Mapper
import com.currencyapp.utils.mapper.RatesToRateDtosMapper
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApplicationModule(private val context: Context) {

    private val PREFS_FILENAME = "com.currencyapp.prefs"

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun provideSharedPreferences(
        context: Context
    ): SharedPreferences {
        return context.getSharedPreferences(PREFS_FILENAME, 0)
    }
}