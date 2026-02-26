package com.devhjs.loge.data.repository

import com.devhjs.loge.core.util.DateUtils
import com.devhjs.loge.domain.model.AiReport
import com.devhjs.loge.domain.model.ChartPoint
import com.devhjs.loge.domain.model.EmotionType
import com.devhjs.loge.domain.model.Stat
import com.devhjs.loge.domain.model.Til
import com.devhjs.loge.domain.repository.TilRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockRepositoryImpl @Inject constructor() : TilRepository {

    private val _mockData = MutableStateFlow<List<Til>>(
        MutableList(10) { i ->
            val index = i + 1
            Til(
                id = (i + 1).toLong(),
                createdAt = System.currentTimeMillis() - (index * 86400000L),
                title = "테테테테스트 제목 $index",
                learned = "오늘 안드로이드 개발에 대해 새로운 것을 배웠습니다. 항목 $i",
                difficult = "특별히 어려운 점은 없었습니다.",
                emotionScore = (50..100).random(),
                emotion = EmotionType.SATISFACTION,
                difficultyLevel = (1..5).random(),
                updatedAt = System.currentTimeMillis(),
                aiFeedBack = "Flexbox는 레이아웃의 기본이에요! flex 속성의 단축 문법(flex: 1 1 0%)을 익히면 더 효율적으로 코딩할 수 있어요. 잘하고 계세요!"
            )
        }
    )

    override fun getAllTil(start: Long, end: Long): Flow<List<Til>> {
        return _mockData.map { list ->
            list.filter { it.createdAt in start..end }.sortedByDescending { it.createdAt }
        }
    }

    override fun getTil(id: Long): Flow<Til> {
        return _mockData.map { list ->
            list.find { it.id == id } ?: list.first()
        }
    }

    override suspend fun saveTil(til: Til) {
        _mockData.update { list ->
            val newId = (list.maxOfOrNull { it.id } ?: 0) + 1
            list + til.copy(id = newId)
        }
    }

    override suspend fun updateTil(til: Til) {
        _mockData.update { list ->
            list.map { if (it.id == til.id) til else it }
        }
    }

    override suspend fun deleteTil(til: Til) {
        _mockData.update { list ->
            list.filter { it.id != til.id }
        }
    }

    override suspend fun deleteTil(id: Long) {
        _mockData.update { list ->
            list.filter { it.id != id }
        }
    }

    override suspend fun deleteAll() {
        _mockData.value = emptyList()
    }

    override suspend fun getAllTils(): List<Til> {
        return _mockData.value
    }

    override fun getMonthlyStats(month: String): Flow<Stat> {
        return _mockData.map { tils ->
             val totalTil = tils.size
             val avgEmotion = if (tils.isEmpty()) 0f else tils.map { it.emotionScore }.average().toFloat()
             val avgScore = if (tils.isEmpty()) 0f else tils.map { it.emotionScore }.average().toFloat()
             val avgDifficulty = if (tils.isEmpty()) 0f else tils.map { it.difficultyLevel }.average().toFloat()
                
             val emotionScoreList = tils.map { til ->
                 ChartPoint(
                     x = DateUtils.getDayOfMonth(til.createdAt).toFloat(), 
                     y = til.emotionScore.toFloat()
                 )
             }.sortedBy { it.x }

             val learnedDates = tils.map { til ->
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
                 aiReport = AiReport(
                     date = System.currentTimeMillis(),
                     emotion = "만족",
                     emotionScore = 4,
                     difficultyLevel = "보통",
                     comment = "테스트용 AI 리포트입니다. 이번 달은 전반적으로 학습량이 훌륭합니다!"
                 )
             )
        }
    }

    override suspend fun syncAllTilsToRemote() {}

    override suspend fun fetchRemoteTilsToLocal() {}
}
