package com.currencyapp.localdb.di

import android.content.Context
import androidx.room.Room
import com.currencyapp.localdb.LocalDatabase
import com.currencyapp.localdb.LocalDatabaseRateDto
import com.currencyapp.localdb.mapper.LocalDatabaseMapper
import com.currencyapp.localdb.repo.LocalDatabaseRepository
import com.currencyapp.localdb.repo.LocalRepository
import com.currencyapp.network.entity.RateDto
import com.currencyapp.utils.mapper.TwoWayMapper
import dagger.Module
import dagger.Provides

@Module
class LocalRepoModule {

    @Provides
    fun provideLocalDatabaseRepository(
        localDatabase: LocalDatabase,
        localDatabaseMapper: TwoWayMapper<LocalDatabaseRateDto, RateDto>
    ): LocalRepository {
        return LocalDatabaseRepository(localDatabase, localDatabaseMapper)
    }

    @Provides
    fun provideAppDatabase(context: Context): LocalDatabase {
        return Room.databaseBuilder(
            context,
            LocalDatabase::class.java,
            "local_db"
        ).build()
    }

    @Provides
    fun provideLocalDatabaseMapper(): TwoWayMapper<LocalDatabaseRateDto, RateDto> {
        return LocalDatabaseMapper()
    }
}