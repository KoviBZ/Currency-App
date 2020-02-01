package com.currencyapp.utils.mapper

interface Mapper<T, R> {
    fun map(from: T): R
}