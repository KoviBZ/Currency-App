package com.currencyapp.utils.mapper

interface TwoWayMapper<T, R> : Mapper<T, R> {

    fun mapRevert(from: R): T
}