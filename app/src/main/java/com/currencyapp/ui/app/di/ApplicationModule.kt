package com.currencyapp.ui.app.di

import android.content.Context
import com.currencyapp.network.utils.BaseSchedulerProvider
import com.currencyapp.network.utils.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val context: Context) {

    @Provides
    fun providesContext(): Context {
        return context
    }

    @Provides
    fun provideSchedulerProvider(): BaseSchedulerProvider {
        return SchedulerProvider()
    }
}