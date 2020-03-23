package com.currencyapp.localrepo.room.di

import android.content.Context
import androidx.room.Room
import com.currencyapp.localrepo.room.LocalDatabase
import dagger.Module
import dagger.Provides

@Module
class LocalRepoModule {

    @Provides
    fun provideAppDatabase(context: Context): LocalDatabase {
        return Room.databaseBuilder(
            context,
            LocalDatabase::class.java,
            "local_db"
        ).build()
    }
}