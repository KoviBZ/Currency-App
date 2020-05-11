package com.currencyapp.network.mapper

import com.currencyapp.network.entity.RateDto
import com.currencyapp.utils.mapper.Mapper
import javax.inject.Inject

class RatesToRateDtosMapper @Inject constructor():
    Mapper<Map.Entry<String, Double>, RateDto> {

    override fun map(from: Map.Entry<String, Double>): RateDto {
        return RateDto(
            from.key,
            from.value
        )
    }
}