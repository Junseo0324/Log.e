package com.devhjs.loge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devhjs.loge.data.local.dao.TilDao
import com.devhjs.loge.data.local.entity.TilEntity

@Database(entities = [TilEntity::class], version = 1, exportSchema = false)
abstract class LogEDatabase : RoomDatabase() {
    abstract fun tilDao(): TilDao
}
