package com.currencyapp.localrepo.room

import androidx.room.*

@Dao
abstract class ItemDao {

    @Insert/*(onConflict = OnConflictStrategy.REPLACE)*/
    abstract fun insert(vararg items: CurrencyItemRoomDto)

    @Update
    abstract fun update(vararg items: CurrencyItemRoomDto)

    @Delete
    abstract fun delete(item: CurrencyItemRoomDto)

    @Query("SELECT * from lastResponse")
    abstract fun getReasons(): List<CurrencyItemRoomDto>
}