package com.devhjs.loge.data.repository

import com.devhjs.loge.data.local.dao.TilDao
import com.devhjs.loge.data.local.entity.TilEntity
import com.devhjs.loge.data.mapper.toDomain
import com.devhjs.loge.data.mapper.toEntity
import com.devhjs.loge.domain.model.ChartPoint
import com.devhjs.loge.domain.model.Stat
import com.devhjs.loge.domain.model.Til
import com.devhjs.loge.domain.repository.TilRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.withContext
import com.devhjs.loge.core.util.DateUtils
import javax.inject.Inject
import kotlinx.coroutines.flow.map as flowMap

class TilRepositoryImpl @Inject constructor(
    private val tilDao: TilDao
) : TilRepository {

    override fun getAllTil(start: Long, end: Long): Flow<List<Til>> {
        return tilDao.getTilsBetween(start, end).flowMap { entities: List<TilEntity> ->
            entities.map { entity -> entity.toDomain() }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getAllTils(): List<Til> {
        return withContext(Dispatchers.IO) {
            tilDao.getAllTils().map { it.toDomain() }
        }
    }

    override fun getTil(id: Long): Flow<Til> {
        return tilDao.getTilById(id).mapNotNull { it.firstOrNull()?.toDomain() }.flowOn(Dispatchers.IO)
    }

    override suspend fun saveTil(til: Til) {
        withContext(Dispatchers.IO) {
            tilDao.insertTil(til.toEntity())
        }
    }

    override suspend fun updateTil(til: Til) {
        withContext(Dispatchers.IO) {
            tilDao.updateTil(til.toEntity())
        }
    }

    override suspend fun deleteTil(til: Til) {
        withContext(Dispatchers.IO) {
            tilDao.deleteTil(til.toEntity())
        }
    }

    override suspend fun deleteTil(id: Long) {
        withContext(Dispatchers.IO) {
            tilDao.deleteTilById(id)
        }
    }

    override suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            tilDao.deleteAll()
        }
    }

    override fun getMonthlyStats(month: String): Flow<Stat> {
        val (startOfMonth, endOfMonth) = DateUtils.getMonthStartEndTimestamps(month)

        return tilDao.getTilsBetween(startOfMonth, endOfMonth).flowMap { entities: List<TilEntity> ->
            val tils: List<Til> = entities.map { entity -> entity.toDomain() }
            
            if (tils.isEmpty()) {
                return@flowMap Stat(
                    date = month,
                    totalTil = 0,
                    avgEmotion = 0f,
                    avgScore = 0f,
                    avgDifficulty = 0f,
                    emotionScoreList = emptyList(),
                    learnedDates = emptyList()
                )
            }

            val totalTil = tils.size
            val avgEmotion = tils.map { it.emotionScore }.average().toFloat() 
            val avgScore = tils.map { it.emotionScore }.average().toFloat()
            val avgDifficulty = tils.map { it.difficultyLevel }.average().toFloat()
            
            val emotionScoreList = tils.map { til: Til ->
                ChartPoint(
                    x = DateUtils.getDayOfMonth(til.createdAt).toFloat(), 
                    y = til.emotionScore.toFloat()
                )
            }.sortedBy { it.x }

            val learnedDates = tils.map { til: Til ->
                DateUtils.formatToIsoDate(til.createdAt)
            }.distinct()

            Stat(
                date = month,
                totalTil = totalTil,
                avgEmotion = avgEmotion,
                avgScore = avgScore,
                avgDifficulty = avgDifficulty,
                emotionScoreList = emotionScoreList,
                learnedDates = learnedDates,
                aiReport = null
            )
        }.flowOn(Dispatchers.IO)
    }
}
