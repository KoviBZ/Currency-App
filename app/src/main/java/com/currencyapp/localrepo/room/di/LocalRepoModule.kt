package com.currencyapp.localrepo.room.di

import android.content.Context
import dagger.Module
import androidx.room.Room
import com.currencyapp.localrepo.room.AppDatabase
import dagger.Provides

@Module
object LocalRepoModule {

    @Provides
    fun provideRoomDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "local_db"
        ).build()
    }
}