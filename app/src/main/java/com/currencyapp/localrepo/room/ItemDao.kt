package com.currencyapp.localrepo.room

import android.content.ClipData.Item
import androidx.room.Delete
import androidx.room.Update
import androidx.room.Dao
import androidx.room.Insert

@Dao
interface ItemDao {

    @Insert
    fun insert(vararg items: Item)

    @Update
    fun update(vararg items: Item)

    @Delete
    fun delete(item: Item)
}