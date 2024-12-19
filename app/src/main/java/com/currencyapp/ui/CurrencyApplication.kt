package com.currencyapp.ui

import android.app.Application
import com.currencyapp.di.allModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class CurrencyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CurrencyApplication)
            modules(allModules)
        }
    }
}