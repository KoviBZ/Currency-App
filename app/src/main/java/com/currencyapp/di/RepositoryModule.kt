package com.currencyapp.di

import com.currencyapp.network.repo.ApiRepository
import com.currencyapp.network.repo.RemoteRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::ApiRepository) { bind<RemoteRepository>() }
}