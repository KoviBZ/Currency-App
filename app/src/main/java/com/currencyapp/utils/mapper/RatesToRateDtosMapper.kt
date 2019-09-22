package com.currencyapp.utils.mapper

import com.currencyapp.dto.RateDto

class RatesToRateDtosMapper: Mapper<Map.Entry<String, Double>, RateDto> {

    override fun map(from: Map.Entry<String, Double>): RateDto {
        return RateDto(
            from.key,
            from.value
        )
    }

}