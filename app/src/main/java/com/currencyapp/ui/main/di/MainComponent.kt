package com.currencyapp.ui.main.di

import com.currencyapp.ui.app.di.ApplicationComponent
import com.currencyapp.ui.main.view.MainActivity
import dagger.Component

@Component(modules = [
    MainModule::class
], dependencies = [
    ApplicationComponent::class
])
interface MainComponent {

    fun inject(mainActivity: MainActivity)
}