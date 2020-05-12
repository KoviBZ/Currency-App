package com.currencyapp.localdb.mapper

import com.currencyapp.localdb.LocalDatabaseRateDto
import com.currencyapp.network.entity.RateDto
import com.currencyapp.utils.mapper.TwoWayMapper
import javax.inject.Inject

class LocalDatabaseMapper @Inject constructor():
    TwoWayMapper<LocalDatabaseRateDto, RateDto> {

    override fun map(from: LocalDatabaseRateDto): RateDto {
        return RateDto(
            from.name,
            from.value
        )
    }

    override fun mapRevert(from: RateDto): LocalDatabaseRateDto {
        return LocalDatabaseRateDto(
            from.key,
            from.value
        )
    }
}