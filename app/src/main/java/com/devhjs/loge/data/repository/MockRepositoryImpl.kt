package com.devhjs.loge.data.repository

import com.devhjs.loge.core.util.DateUtils
import com.devhjs.loge.domain.model.AiReport
import com.devhjs.loge.domain.model.ChartPoint
import com.devhjs.loge.domain.model.EmotionType
import com.devhjs.loge.domain.model.Stat
import com.devhjs.loge.domain.model.Til
import com.devhjs.loge.domain.repository.TilRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MockRepositoryImpl @Inject constructor() : TilRepository {

    private val mockData = mutableListOf<Til>().apply {
        var idCounter = 1L
        for (i in 0 until 10) {
            add(
                Til(
                    id = idCounter++,
                    createdAt = System.currentTimeMillis() - (i * 86400000L),
                    title = "테테테테스트 제목 $i",
                    learned = "오늘 안드로이드 개발에 대해 새로운 것을 배웠습니다. 항목 $i",
                    difficult = "특별히 어려운 점은 없었습니다.",
                    emotionScore = (50..100).random(),
                    emotion = EmotionType.SATISFACTION,
                    difficultyLevel = (1..5).random(),
                    updatedAt = System.currentTimeMillis(),
                    aiFeedBack = "Flexbox는 레이아웃의 기본이에요! flex 속성의 단축 문법(flex: 1 1 0%)을 익히면 더 효율적으로 코딩할 수 있어요. 잘하고 계세요!"
                )
            )
        }
    }

    override fun getAllTil(start: Long, end: Long): Flow<List<Til>> = flow {
        emit(mockData.filter { it.createdAt in start..end })
    }

    override fun getTil(id: Long): Flow<Til> = flow {
        val item = mockData.find { it.id == id } ?: mockData.first()
        emit(item)
    }

    override suspend fun saveTil(til: Til) {
        mockData.add(til.copy(id = (mockData.maxOfOrNull { it.id } ?: 0) + 1))
    }

    override suspend fun updateTil(til: Til) {
        val index = mockData.indexOfFirst { it.id == til.id }
        if (index != -1) {
            mockData[index] = til
        }
    }

    override suspend fun deleteTil(til: Til) {
        mockData.removeAll { it.id == til.id }
    }

    override suspend fun deleteTil(id: Long) {
        mockData.removeAt(index = id.toInt())
    }

    override suspend fun deleteAll() {
        mockData.clear()
    }

    override suspend fun getAllTils(): List<Til> {
        return mockData.toList()
    }

    override fun getMonthlyStats(month: String): Flow<Stat> = flow {
         val tils = mockData
         
         val totalTil = tils.size
         val avgEmotion = tils.map { it.emotionScore }.average().toFloat()
         val avgScore = tils.map { it.emotionScore }.average().toFloat()
         val avgDifficulty = tils.map { it.difficultyLevel }.average().toFloat()
            
         val emotionScoreList = tils.map { til ->
             ChartPoint(
                 x = DateUtils.getDayOfMonth(til.createdAt).toFloat(), 
                 y = til.emotionScore.toFloat()
             )
         }.sortedBy { it.x }

         val learnedDates = tils.map { til ->
             DateUtils.formatToIsoDate(til.createdAt)
         }.distinct()

         emit(
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
         )
    }

    override suspend fun syncAllTilsToRemote() {
        // Mock 이라 필요없음.
    }
}
