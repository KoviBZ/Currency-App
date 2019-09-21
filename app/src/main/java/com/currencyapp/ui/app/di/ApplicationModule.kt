package com.currencyapp.ui.app.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private var context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }
}