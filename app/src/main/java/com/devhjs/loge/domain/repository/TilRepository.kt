package com.devhjs.loge.domain.repository

import com.devhjs.loge.domain.model.Stat
import com.devhjs.loge.domain.model.Til
import kotlinx.coroutines.flow.Flow

interface TilRepository {
    fun getAllTil(start: Long, end: Long): Flow<List<Til>>
    suspend fun getAllTils(): List<Til>
    fun getTil(id: Long): Flow<Til>
    suspend fun saveTil(til: Til)
    suspend fun updateTil(til: Til)
    suspend fun deleteTil(til: Til)
    suspend fun deleteTil(id: Long)
    suspend fun deleteAll()
    fun getMonthlyStats(month: String): Flow<Stat>
    suspend fun syncAllTilsToRemote()
    suspend fun fetchRemoteTilsToLocal()
}
