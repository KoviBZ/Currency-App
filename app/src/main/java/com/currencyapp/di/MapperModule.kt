package com.currencyapp.di

import com.currencyapp.network.entity.RateDto
import com.currencyapp.network.mapper.RatesToRateDtosMapper
import com.currencyapp.utils.mapper.Mapper
import org.koin.dsl.module

val mapperModule = module {
    single<Mapper<Map.Entry<String, Double>, RateDto>> { RatesToRateDtosMapper() }
}