package com.currencyapp.ui.main.di

import com.currencyapp.ui.main.view.MainActivity
import dagger.Subcomponent

@Subcomponent(modules = [MainModule::class])
interface MainComponent {

    //inject
    fun inject(mainActivity: MainActivity)
}