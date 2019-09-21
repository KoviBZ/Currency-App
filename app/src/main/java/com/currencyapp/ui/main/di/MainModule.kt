package com.currencyapp.ui.main.di

import com.currencyapp.network.CurrencyApi
import com.currencyapp.ui.main.model.MainModel
import com.currencyapp.ui.main.presenter.MainPresenter
import dagger.Module
import dagger.Provides

@Module
object MainModule {

    @Provides
    fun provideMainPresenter(model: MainModel): MainPresenter =
        MainPresenter(model)

    @Provides
    fun provideMainModel(currencyApi: CurrencyApi): MainModel =
        MainModel(currencyApi)
}