package com.currencyapp.localdb.mapper

import com.currencyapp.localdb.LocalDatabaseRateDto
import com.currencyapp.network.entity.RateDto
import com.currencyapp.utils.mapper.Mapper
import javax.inject.Inject

class LocalDatabaseMapper @Inject constructor():
    Mapper<LocalDatabaseRateDto, RateDto> {

    override fun map(from: LocalDatabaseRateDto): RateDto {
        return RateDto(
            from.name,
            from.value
        )
    }
}