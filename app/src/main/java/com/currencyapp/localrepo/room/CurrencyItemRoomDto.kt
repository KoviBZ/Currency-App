package com.currencyapp.localrepo.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lastResponse")
data class CurrencyItemRoomDto(

    @PrimaryKey
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "value")
    var value: Double
)