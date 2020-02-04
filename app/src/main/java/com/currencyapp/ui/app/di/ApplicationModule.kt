package com.currencyapp.ui.app.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides

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