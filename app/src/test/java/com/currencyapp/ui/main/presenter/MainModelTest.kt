package com.currencyapp.ui.main.presenter

import com.currencyapp.network.CurrencyApi
import com.currencyapp.network.entity.CurrencyResponse
import com.currencyapp.network.entity.RateDto
import com.currencyapp.ui.main.model.DefaultMainModel
import com.currencyapp.ui.main.model.MainModel
import com.currencyapp.utils.mapper.Mapper
import com.currencyapp.utils.mapper.RatesToRateDtosMapper
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.subscribers.TestSubscriber
import org.mockito.BDDMockito.any
import org.mockito.BDDMockito.given
import org.mockito.Matchers
import org.mockito.Mockito.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class MainModelTest : Spek({

    val api: CurrencyApi by memoized { mock(CurrencyApi::class.java) }
    val mapper: Mapper<Map.Entry<String, Double>, RateDto> by memoized { RatesToRateDtosMapper() }

    val model: MainModel by memoized { DefaultMainModel(api, mapper) }

    describe("retrieve currency response") {

        val observer = TestObserver<List<RateDto>>()
        val response = apiResponse()

        beforeEachTest {
            given(api.getCurrencies(anyString())).willReturn(Single.just(response))
//            given(mapper.map(any())).willReturn(mock(RateDto::class.java))

            model.retrieveCurrencyResponse(anyString()).subscribe(observer)
        }

        it("should complete") {
            observer.assertComplete()
        }

        it("should return same amount as rates map entries") {
            observer.assertValueCount(response.rates.size)
        }
    }
})

private fun apiResponse(): CurrencyResponse {
    val ratesMap = LinkedHashMap<String, Double>()
    ratesMap["EUR"] = 1.0
    ratesMap["PLN"] = 4.32

    return CurrencyResponse(
        "base",
        "2019-08-09",
        ratesMap
    )
}

private fun rateDtoItem(key: String, value: Double): RateDto =
    RateDto(key, value)