package com.currencyapp.network.repo

import com.currencyapp.network.CurrencyApi
import com.currencyapp.network.entity.CurrencyResponse
import com.currencyapp.network.entity.RateDto
import com.currencyapp.utils.mapper.Mapper
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.jupiter.api.Assertions.assertEquals
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class RemoteRepositoryTest : Spek({

    val api: CurrencyApi by memoized { mock<CurrencyApi>() }
    val mapper: Mapper<Map.Entry<String, Double>, RateDto> by memoized { mock<Mapper<Map.Entry<String, Double>, RateDto>>() }

    val repository: RemoteRepository by memoized { ApiRepository(api, mapper) }

    val currencyName = "sth"

    describe("insert data") {

        lateinit var observer: TestObserver<List<RateDto>>
        val response = prepareApiResponse()

        beforeEachTest {
            observer = TestObserver<List<RateDto>>()

            given(api.getCurrencies(any())).willReturn(Single.just(response))

            repository.getCurrencyResponse(currencyName).subscribe(observer)
        }

        it("should complete") {
            observer.assertComplete()
        }

        it("should return amount of response entries + 1 default") {
            assertEquals(observer.values()[0].size, response.rates.size + 1)
        }

        it("should first item key be equal to request currency") {
            assertEquals(observer.values()[0][0].key, currencyName)
        }
    }
})

private fun prepareApiResponse(): CurrencyResponse {
    val currencyMap = LinkedHashMap<String, Double>()

    currencyMap["USD"] = 1.11
    currencyMap["GBP"] = 1.71

    return CurrencyResponse("sth", currencyMap)
}