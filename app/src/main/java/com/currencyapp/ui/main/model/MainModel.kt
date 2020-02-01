package com.currencyapp.ui.main.model

import android.content.SharedPreferences
import com.currencyapp.localrepo.RateDto
import com.currencyapp.localrepo.RateDtoList
import com.currencyapp.network.CurrencyApi
import com.currencyapp.utils.mapper.Mapper
import com.google.gson.Gson
import io.reactivex.Single

class MainModel(
    private val currencyApi: CurrencyApi,
    private val prefs: SharedPreferences,
    private val mapper: Mapper<Map.Entry<String, Double>, RateDto>
) {

    val RESPONSE_JSON = "RESPONSE_JSON"
    val MULTIPLIER_JSON = "MULTIPLIER_JSON"
    val BASE_CURRENCY_JSON = "BASE_CURRENCY_JSON"

    fun retrieveCurrencyResponse(multiplier: Double): Single<ArrayList<RateDto>> {
        return currencyApi.getCurrencies(tempGetBaseCurrency())
            .map { response ->
                val list = ArrayList<RateDto>()
                list.add(RateDto(tempGetBaseCurrency(), multiplier))

                response.rates.iterator().forEach {
                    list.add(mapper.map(it))
                }

                tempSaveResponse(list)
                list
            }
    }

    fun tempSaveCurrentMultiplier(multiplier: Double) {
        val gson = Gson()
        val json = gson.toJson(multiplier)
        prefs.edit().putString(MULTIPLIER_JSON, json).apply()
    }

    fun tempGetCurrentMultiplier(): Double {
        val gson = Gson()
        val jsonString = prefs.getString(MULTIPLIER_JSON, "")
        return gson.fromJson(jsonString, Double::class.java)
    }

    fun tempSaveBaseCurrency(baseCurrency: String) {
        prefs.edit().putString(BASE_CURRENCY_JSON, baseCurrency).apply()
    }

    fun tempGetBaseCurrency(): String {
        return prefs.getString(BASE_CURRENCY_JSON, "EUR") ?: "EUR"
    }

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