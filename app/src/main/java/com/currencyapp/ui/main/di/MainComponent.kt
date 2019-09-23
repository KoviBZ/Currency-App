package com.currencyapp.ui.main.di

import com.currencyapp.ui.main.presenter.MainPresenter
import com.currencyapp.ui.main.view.MainActivity
import dagger.Component

@Component(modules = [
    MainModule::class
])
interface MainComponent {

    fun presenter(): MainPresenter

    fun inject(mainActivity: MainActivity)

//    class Builder {
//
//        fun build(): MainComponent.Builder
//    }
}