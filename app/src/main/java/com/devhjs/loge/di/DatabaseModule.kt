package com.devhjs.loge.di

import android.content.Context
import androidx.room.Room
import com.devhjs.loge.data.local.LogEDatabase
import com.devhjs.loge.data.local.dao.TilDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideLogEDatabase(
        @ApplicationContext context: Context
    ): LogEDatabase {
        return Room.databaseBuilder(
            context,
            LogEDatabase::class.java,
            "loge_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTilDao(database: LogEDatabase): TilDao {
        return database.tilDao()
    }
}
