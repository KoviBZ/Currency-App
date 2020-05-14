package com.currencyapp.localdb.mapper

import com.currencyapp.localdb.LocalDatabaseRateDto
import com.currencyapp.network.entity.RateDto
import com.currencyapp.utils.mapper.TwoWayMapper
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class LocalDatabaseMapperTest : Spek({

    val mapper: TwoWayMapper<LocalDatabaseRateDto, RateDto> by memoized { LocalDatabaseMapper() }

    describe("map") {

        val localRateDto = LocalDatabaseRateDto("usd", 1.1)
        lateinit var item: RateDto

        beforeEachTest {
            item = mapper.map(localRateDto)
        }

        it("item key should match localRateDto name") {
            assert(item.key == localRateDto.name)
        }

        it("item value should match localRateDto value") {
            assert(item.value == localRateDto.value)
        }
    }

    describe("map revert") {

        val rateDto = RateDto("usd", 1.1)
        lateinit var item: LocalDatabaseRateDto

        beforeEachTest {
            item = mapper.mapRevert(rateDto)
        }

        it("item name should match rateDto key") {
            assert(item.name == rateDto.key)
        }

        it("item value should match rateDto value") {
            assert(item.value == rateDto.value)
        }
    }
})