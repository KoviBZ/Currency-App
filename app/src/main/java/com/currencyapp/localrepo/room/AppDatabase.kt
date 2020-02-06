package com.currencyapp.localrepo.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CurrencyItemRoomDto::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val itemDao: ItemDao
}