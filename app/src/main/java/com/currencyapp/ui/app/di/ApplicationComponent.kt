package com.currencyapp.ui.app.di

import com.currencyapp.network.di.NetworkModule
import com.currencyapp.ui.main.view.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [
    ApplicationModule::class,
    NetworkModule::class
])
@Singleton
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)
}