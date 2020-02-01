package com.currencyapp.utils.mapper

import com.currencyapp.localrepo.RateDto
import javax.inject.Inject

class RatesToRateDtosMapper @Inject constructor(): Mapper<Map.Entry<String, Double>, RateDto> {

    override fun map(from: Map.Entry<String, Double>): RateDto {
        return RateDto(
            from.key,
            from.value
        )
    }
}