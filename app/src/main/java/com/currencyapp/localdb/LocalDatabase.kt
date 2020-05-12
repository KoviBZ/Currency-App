package com.currencyapp.localdb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LocalDatabaseRateDto::class],
    version = 1
)
abstract class LocalDatabase : RoomDatabase() {
    abstract val itemDao: ItemDao
}