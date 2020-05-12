package com.currencyapp.ui.main.di

import com.currencyapp.localdb.di.LocalRepoModule
import com.currencyapp.localdb.repo.LocalRepository
import com.currencyapp.network.di.NetworkModule
import com.currencyapp.network.repo.RemoteRepository
import com.currencyapp.network.utils.BaseSchedulerProvider
import com.currencyapp.ui.main.model.DefaultMainModel
import com.currencyapp.ui.main.model.MainModel
import com.currencyapp.ui.main.presenter.MainPresenter
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        NetworkModule::class,
        LocalRepoModule::class
    ]
)
class MainModule {

    @Provides
    fun provideMainPresenter(
        schedulerProvider: BaseSchedulerProvider,
        model: MainModel
    ): MainPresenter = MainPresenter(schedulerProvider, model)

    @Provides
    fun provideMainModel(
        remoteRepository: RemoteRepository,
        localRepository: LocalRepository
    ): MainModel = DefaultMainModel(remoteRepository, localRepository)
}