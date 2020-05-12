package com.currencyapp.localdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import io.reactivex.Single

@Dao
abstract class ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg items: LocalDatabaseRateDto)

    @Update
    abstract fun update(vararg items: LocalDatabaseRateDto)

    @Delete
    abstract fun delete(item: LocalDatabaseRateDto)

    @Query("SELECT * from lastResponse")
    abstract fun getCurrencyItems(): Single<List<LocalDatabaseRateDto>>
}