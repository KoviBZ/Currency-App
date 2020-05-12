package com.currencyapp.localdb.di

import android.content.Context
import androidx.room.Room
import com.currencyapp.localdb.LocalDatabase
import com.currencyapp.localdb.LocalDatabaseRateDto
import com.currencyapp.localdb.mapper.LocalDatabaseMapper
import com.currencyapp.localdb.repo.LocalDatabaseRepository
import com.currencyapp.localdb.repo.LocalRepository
import com.currencyapp.network.entity.RateDto
import com.currencyapp.utils.mapper.Mapper
import dagger.Module
import dagger.Provides

@Module
class LocalRepoModule {

    @Provides
    fun provideLocalDatabaseRepository(
        localDatabase: LocalDatabase,
        mapper: Mapper<LocalDatabaseRateDto, RateDto>
    ): LocalRepository {
        return LocalDatabaseRepository(localDatabase, mapper)
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
    fun provideLocalDatabaseMapper(): Mapper<LocalDatabaseRateDto, RateDto> {
        return LocalDatabaseMapper()
    }
}