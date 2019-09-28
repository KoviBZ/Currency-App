package com.currencyapp.ui.app.di

import com.currencyapp.ui.main.di.MainComponent
import com.currencyapp.ui.main.view.MainActivity
import dagger.Component

@Component(modules = [
    ApplicationModule::class
])
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)

//    @Component.Builder
//    interface Builder {
//
//        fun build(context: Context): ApplicationComponent.Builder
//    }
}