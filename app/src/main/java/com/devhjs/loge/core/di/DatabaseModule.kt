package com.devhjs.loge.core.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.devhjs.loge.data.local.LogEDatabase
import com.devhjs.loge.data.local.dao.MonthlyReviewDao
import com.devhjs.loge.data.local.dao.TilDao
import com.devhjs.loge.data.local.dao.UserDao
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
        val MIGRATION_6_7 = object : Migration(6, 7) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE til ADD COLUMN tomorrowPlan TEXT NOT NULL DEFAULT ''")
            }
        }

        return Room.databaseBuilder(
            context,
            LogEDatabase::class.java,
            "loge_database"
        )
        .addMigrations(MIGRATION_6_7)
        .fallbackToDestructiveMigration()
        .build()
    }



    @Provides
    @Singleton
    fun provideTilDao(database: LogEDatabase): TilDao {
        return database.tilDao()
    }


    @Provides
    @Singleton
    fun provideUserDao(database: LogEDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideMonthlyReviewDao(database: LogEDatabase): MonthlyReviewDao {
        return database.monthlyReviewDao()
    }
}
