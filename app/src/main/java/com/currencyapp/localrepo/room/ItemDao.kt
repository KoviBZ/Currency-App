package com.currencyapp.localrepo.room

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
    abstract fun insert(vararg items: CurrencyItemRoomDto)

    @Update
    abstract fun update(vararg items: CurrencyItemRoomDto)

    @Delete
    abstract fun delete(item: CurrencyItemRoomDto)

    @Query("SELECT * from lastResponse")
    abstract fun getCurrencyItems(): Single<List<CurrencyItemRoomDto>>
}