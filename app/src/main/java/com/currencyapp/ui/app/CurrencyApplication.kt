package com.currencyapp.ui.app

import android.app.Application
import com.currencyapp.ui.app.di.ApplicationComponent
import com.currencyapp.ui.app.di.ApplicationModule
import com.currencyapp.ui.app.di.DaggerApplicationComponent

class CurrencyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        applicationComponent = buildComponent()
    }

    private fun buildComponent(): ApplicationComponent =
        DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(applicationContext))
            .build()

    companion object {

        private lateinit var applicationComponent: ApplicationComponent

        @JvmStatic
        fun getApplicationComponent() = applicationComponent
    }
}