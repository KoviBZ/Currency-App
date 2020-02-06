package com.currencyapp.ui.main.di

import android.content.SharedPreferences
import com.currencyapp.localrepo.RateDto
import com.currencyapp.localrepo.room.AppDatabase
import com.currencyapp.localrepo.room.di.LocalRepoModule
import com.currencyapp.network.CurrencyApi
import com.currencyapp.network.di.NetworkModule
import com.currencyapp.ui.main.model.DefaultMainModel
import com.currencyapp.ui.main.model.MainModel
import com.currencyapp.ui.main.presenter.MainPresenter
import com.currencyapp.utils.mapper.Mapper
import com.currencyapp.utils.mapper.RatesToRateDtosMapper
import dagger.Module
import dagger.Provides

@Module(includes = [
    NetworkModule::class,
    LocalRepoModule::class
])
class MainModule {

    @Provides
    fun provideMainPresenter(model: MainModel): MainPresenter = MainPresenter(model)

    @Provides
    fun provideMainModel(
        currencyApi: CurrencyApi,
        sharedPreferences: SharedPreferences,
        roomDatabase: AppDatabase,
        mapper: Mapper<Map.Entry<String, Double>, RateDto>
    ): MainModel = DefaultMainModel(currencyApi, sharedPreferences, roomDatabase, mapper)

    @Provides
    fun provideRatesToRateDtosMapper(): Mapper<Map.Entry<String, Double>, RateDto> =
        RatesToRateDtosMapper()
}