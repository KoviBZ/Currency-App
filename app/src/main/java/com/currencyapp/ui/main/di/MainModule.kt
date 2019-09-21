package com.currencyapp.ui.main.di

import com.currencyapp.network.CurrencyApi
import com.currencyapp.network.di.NetworkModule
import com.currencyapp.ui.main.model.MainModel
import com.currencyapp.ui.main.presenter.MainPresenter
import dagger.Module
import dagger.Provides

@Module(includes = [NetworkModule::class])
object MainModule {

    @Provides
    fun provideMainPresenter(model: MainModel): MainPresenter =
        MainPresenter(model)

    @Provides
    fun provideMainModel(currencyApi: CurrencyApi): MainModel =
        MainModel(currencyApi)
}