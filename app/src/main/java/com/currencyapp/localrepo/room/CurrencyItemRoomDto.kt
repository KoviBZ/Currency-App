package com.currencyapp.localrepo.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lastResponse")
data class CurrencyItemRoomDto(

    @PrimaryKey(autoGenerate = true)
    var id: Long,
    @ColumnInfo(name = "name")
    var name: String, // 2018-09-06
    @ColumnInfo(name = "value")
    var value: Double
)