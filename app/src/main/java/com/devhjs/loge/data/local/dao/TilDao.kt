package com.devhjs.loge.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.devhjs.loge.data.local.entity.TilEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TilDao {

    @Query("SELECT * FROM til WHERE createdAt BETWEEN :start AND :end ORDER BY createdAt DESC")
    fun getTilsBetween(start: Long, end: Long): Flow<List<TilEntity>>

    @Query("SELECT * FROM til WHERE id = :id")
    fun getTilById(id: Long): Flow<List<TilEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTil(til: TilEntity)

    @Update
    fun updateTil(til: TilEntity)

    @Delete
    fun deleteTil(til: TilEntity)

    @Query("DELETE FROM til WHERE id = :id")
    fun deleteTilById(id: Long)
}
