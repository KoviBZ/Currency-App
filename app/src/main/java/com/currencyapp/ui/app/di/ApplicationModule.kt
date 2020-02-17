package com.currencyapp.ui.app.di

import com.currencyapp.network.utils.BaseSchedulerProvider
import com.currencyapp.network.utils.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    @Provides
    fun provideSchedulerProvider(): BaseSchedulerProvider {
        return SchedulerProvider()
    }
}