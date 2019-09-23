package com.currencyapp.utils.mapper

import com.currencyapp.localrepo.RateDto

class RatesToRateDtosMapper: Mapper<Map.Entry<String, Double>, RateDto> {

    override fun map(from: Map.Entry<String, Double>): RateDto {
        return RateDto(
            from.key,
            from.value
        )
    }

    override fun mapWithMultiplier(from: Map.Entry<String, Double>, multiplier: Double): RateDto {
        return RateDto(
            from.key,
            from.value * multiplier
        )
    }
}