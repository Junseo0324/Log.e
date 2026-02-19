package com.devhjs.loge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devhjs.loge.data.local.dao.MonthlyReviewDao
import com.devhjs.loge.data.local.dao.TilDao
import com.devhjs.loge.data.local.dao.UserDao
import com.devhjs.loge.data.local.entity.MonthlyReviewEntity
import com.devhjs.loge.data.local.entity.TilEntity
import com.devhjs.loge.data.local.entity.UserEntity


@Database(entities = [TilEntity::class, UserEntity::class, MonthlyReviewEntity::class], version = 6, exportSchema = false)
abstract class LogEDatabase : RoomDatabase() {
    abstract fun tilDao(): TilDao
    abstract fun userDao(): UserDao
    abstract fun monthlyReviewDao(): MonthlyReviewDao
}
