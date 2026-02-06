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
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlinx.coroutines.flow.map as flowMap

class TilRepositoryImpl @Inject constructor(
    private val tilDao: TilDao
) : TilRepository {

    override fun getAllTil(start: Long, end: Long): Flow<List<Til>> {
        return tilDao.getTilsBetween(start, end).flowMap { entities: List<TilEntity> ->
            entities.map { entity -> entity.toDomain() }
        }
    }

    override fun getTil(id: Long): Flow<Til> {
        return tilDao.getTilById(id).mapNotNull { it.firstOrNull()?.toDomain() }
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

    override fun getMonthlyStats(month: String): Flow<Stat> {
        val yearMonth = YearMonth.parse(month)
        val startOfMonth = yearMonth.atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val endOfMonth = yearMonth.atEndOfMonth().atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

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
                val date = Instant.ofEpochMilli(til.createdAt)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                ChartPoint(
                    x = date.dayOfMonth.toFloat(), 
                    y = til.emotionScore.toFloat()
                )
            }.sortedBy { it.x }

            val learnedDates = tils.map { til: Til ->
                Instant.ofEpochMilli(til.createdAt)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                    .format(DateTimeFormatter.ISO_DATE)
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
        }
    }
}
