package com.currencyapp.ui.app.di

import com.currencyapp.ui.main.di.MainComponent
import com.currencyapp.ui.main.di.MainModule
import dagger.Component

@Component(
    modules = [
        ApplicationModule::class
    ]
)
interface ApplicationComponent {

    fun plusMainComponent(mainModule: MainModule): MainComponent
}