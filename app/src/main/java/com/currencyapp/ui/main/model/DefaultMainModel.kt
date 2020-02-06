package com.currencyapp.ui.main.model

import android.content.SharedPreferences
import androidx.room.RoomDatabase
import com.currencyapp.localrepo.RateDto
import com.currencyapp.localrepo.RateDtoList
import com.currencyapp.localrepo.room.AppDatabase
import com.currencyapp.network.CurrencyApi
import com.currencyapp.utils.mapper.Mapper
import com.google.gson.Gson
import io.reactivex.Single

const val BASE_MULTIPLIER = 1.0

class DefaultMainModel(
    private val currencyApi: CurrencyApi,
    private val prefs: SharedPreferences, //TODO remove
    private val roomDatabase: AppDatabase,
    private val mapper: Mapper<Map.Entry<String, Double>, RateDto>
) : MainModel {

    val RESPONSE_JSON = "RESPONSE_JSON"
    val MULTIPLIER_JSON = "MULTIPLIER_JSON"
    val BASE_CURRENCY_JSON = "BASE_CURRENCY_JSON"

    override fun retrieveCurrencyResponse(currency: String): Single<ArrayList<RateDto>> {
        return currencyApi.getCurrencies(currency)
            .map { response ->
                val list = ArrayList<RateDto>()
                list.add(RateDto(currency, BASE_MULTIPLIER))

                response.rates.iterator().forEach {
                    list.add(mapper.map(it))
                }

                tempSaveResponse(list)
                list
            }
    }

//    fun tempGetBaseCurrency(): String {
//        return prefs.getString(BASE_CURRENCY_JSON, "EUR") ?: "EUR"
//    }

    fun tempSaveResponse(response: ArrayList<RateDto>) {
        val gson = Gson()
        val json = gson.toJson(response)
        prefs.edit()?.putString(RESPONSE_JSON, json)?.apply()
    }

    fun tempGetResponse(): ArrayList<RateDto> {
        val gson = Gson()
        val jsonString = prefs.getString(RESPONSE_JSON, "")
        return gson.fromJson(jsonString, RateDtoList::class.java)
    }
}