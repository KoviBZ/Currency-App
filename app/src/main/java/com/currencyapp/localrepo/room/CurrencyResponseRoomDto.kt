package com.currencyapp.localrepo.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lastResponse")
data class CurrencyResponseRoomDto(

    @PrimaryKey var id: Long,
    @ColumnInfo(name = "base") var base: String,
    @ColumnInfo(name = "date") var date: String, // 2018-09-06
    @ColumnInfo(name = "rates") var rates: LinkedHashMap<String, Double>
)