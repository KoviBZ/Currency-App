package com.currencyapp.localrepo.room.mapper

import com.currencyapp.localrepo.room.CurrencyItemRoomDto
import com.currencyapp.network.entity.RateDto
import com.currencyapp.utils.mapper.Mapper
import javax.inject.Inject

class LocalDatabaseRateDtosMapper @Inject constructor():
    Mapper<CurrencyItemRoomDto, RateDto> {

    override fun map(from: CurrencyItemRoomDto): RateDto {
        return RateDto(
            from.name,
            from.value
        )
    }
}