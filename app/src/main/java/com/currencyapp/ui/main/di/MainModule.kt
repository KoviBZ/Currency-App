package com.currencyapp.ui.main.di

import com.currencyapp.localdb.di.LocalRepoModule
import com.currencyapp.localdb.repo.LocalRepository
import com.currencyapp.network.CurrencyApi
import com.currencyapp.network.di.NetworkModule
import com.currencyapp.network.entity.RateDto
import com.currencyapp.network.mapper.RatesToRateDtosMapper
import com.currencyapp.network.utils.BaseSchedulerProvider
import com.currencyapp.ui.main.model.DefaultMainModel
import com.currencyapp.ui.main.model.MainModel
import com.currencyapp.ui.main.presenter.MainPresenter
import com.currencyapp.utils.mapper.Mapper
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        NetworkModule::class,
        LocalRepoModule::class
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
        localRepository: LocalRepository,
        mapper: Mapper<Map.Entry<String, Double>, RateDto>
    ): MainModel = DefaultMainModel(currencyApi, localRepository, mapper)

    @Provides
    fun provideRatesToRateDtosMapper(): Mapper<Map.Entry<String, Double>, RateDto> =
        RatesToRateDtosMapper()
}