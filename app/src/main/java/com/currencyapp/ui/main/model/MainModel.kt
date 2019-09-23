package com.currencyapp.ui.main.model

import android.content.SharedPreferences
import com.currencyapp.localrepo.CurrencyResponse
import com.currencyapp.network.CurrencyApi
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit
import com.google.gson.Gson

class MainModel(
    private val currencyApi: CurrencyApi,
    private val prefs: SharedPreferences?
) {

    val RESPONSE_JSON = "RESPONSE_JSON"
    var currentMultiplier: Double = 100.0

    val subject: BehaviorSubject<CurrencyResponse> = BehaviorSubject.create()

    fun retrieveCurrencyResponse(baseCurrency: String): Single<CurrencyResponse> {
        return currencyApi.getCurrencies(baseCurrency)
    }

    fun tempSaveResponse(response: CurrencyResponse) {
        val gson = Gson()
        val json = gson.toJson(response)
        prefs?.edit()?.putString(RESPONSE_JSON, json)?.apply()
    }

    fun tempGetResponse(): CurrencyResponse {
        val gson = Gson()
        val jsonString = prefs?.getString(RESPONSE_JSON, "")
        return gson.fromJson(jsonString, CurrencyResponse::class.java)
    }
}