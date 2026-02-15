package com.devhjs.loge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devhjs.loge.data.local.dao.TilDao
import com.devhjs.loge.data.local.dao.UserDao
import com.devhjs.loge.data.local.entity.TilEntity
import com.devhjs.loge.data.local.entity.UserEntity


@Database(entities = [TilEntity::class, UserEntity::class], version = 5, exportSchema = false)
abstract class LogEDatabase : RoomDatabase() {
    abstract fun tilDao(): TilDao
    abstract fun userDao(): UserDao
}
