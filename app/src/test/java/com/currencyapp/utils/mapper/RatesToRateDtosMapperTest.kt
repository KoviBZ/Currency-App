package com.currencyapp.utils.mapper

import com.currencyapp.network.entity.RateDto
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class RatesToRateDtosMapperTest : Spek({

    val mapper: Mapper<Map.Entry<String, Double>, RateDto> by memoized { RatesToRateDtosMapper() }

    describe("map") {

        val map = HashMap<String, Double>()
        map["PLN"] = 3.78

        var item: RateDto

        beforeEachTest {
            item = mapper.map(map.entries.first())
        }

//        it("item name should match entry key") {
//            assert(item.key == map.keys.first())
//        }
//
//        it("item name should match entry key") {
//            assert(item.value == map[item.key])
//        }
    }
})