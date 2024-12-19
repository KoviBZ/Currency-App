package com.currencyapp.di

import com.currencyapp.network.entity.RateDto
import com.currencyapp.network.mapper.RatesToRateDtosMapper
import com.currencyapp.network.repo.ApiRepository
import com.currencyapp.network.repo.RemoteRepository
import com.currencyapp.utils.mapper.Mapper
import org.koin.dsl.module

val repositoryModule = module {
    single<RemoteRepository> { get<ApiRepository>() }

    single<Mapper<Map.Entry<String, Double>, RateDto>> { RatesToRateDtosMapper() }
}