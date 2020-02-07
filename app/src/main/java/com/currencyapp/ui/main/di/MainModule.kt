package com.currencyapp.ui.main.di

import com.currencyapp.network.CurrencyApi
import com.currencyapp.network.di.NetworkModule
import com.currencyapp.network.entity.RateDto
import com.currencyapp.network.utils.BaseSchedulerProvider
import com.currencyapp.ui.main.model.DefaultMainModel
import com.currencyapp.ui.main.model.MainModel
import com.currencyapp.ui.main.presenter.MainPresenter
import com.currencyapp.utils.mapper.Mapper
import com.currencyapp.utils.mapper.RatesToRateDtosMapper
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        NetworkModule::class
    ]
)
class MainModule {

    @Provides
    fun provideMainPresenter(
        schedulerProvider: BaseSchedulerProvider,
        model: MainModel
    ): MainPresenter = MainPresenter(schedulerProvider, model)

    @Provides
    fun provideMainModel(
        currencyApi: CurrencyApi,
        mapper: Mapper<Map.Entry<String, Double>, RateDto>
    ): MainModel = DefaultMainModel(currencyApi, mapper)

    @Provides
    fun provideRatesToRateDtosMapper(): Mapper<Map.Entry<String, Double>, RateDto> =
        RatesToRateDtosMapper()
}