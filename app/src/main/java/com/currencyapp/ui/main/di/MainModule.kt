package com.currencyapp.ui.main.di

import com.currencyapp.dto.RateDto
import com.currencyapp.network.CurrencyApi
import com.currencyapp.network.di.NetworkModule
import com.currencyapp.ui.main.model.MainModel
import com.currencyapp.ui.main.presenter.MainPresenter
import com.currencyapp.utils.mapper.Mapper
import com.currencyapp.utils.mapper.RatesToRateDtosMapper
import dagger.Module
import dagger.Provides

@Module(includes = [NetworkModule::class])
object MainModule {

    @Provides
    fun provideMainPresenter(
        model: MainModel,
        mapper: Mapper<Map.Entry<String, Double>, RateDto>
    ): MainPresenter =
        MainPresenter(model, mapper)

    @Provides
    fun provideMainModel(currencyApi: CurrencyApi): MainModel =
        MainModel(currencyApi)

    @Provides
    fun provideRatesToRateDtosMapper(): Mapper<Map.Entry<String, Double>, RateDto> =
        RatesToRateDtosMapper()
}