package com.currencyapp.localrepo.room.di

import android.content.Context
import dagger.Module
import androidx.room.Room
import com.currencyapp.localrepo.room.AppDatabase
import com.currencyapp.ui.app.di.ApplicationModule
import dagger.Provides

@Module(includes = [ApplicationModule::class])
object LocalRepoModule {

//    @Provides
//    fun provideRoomDatabase(context: Context): AppDatabase {
//        return Room.databaseBuilder(
//            context,
//            AppDatabase::class.java,
//            "local_db"
//        ).build()
//    }
}