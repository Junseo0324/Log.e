package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.model.AiReport
import com.devhjs.loge.domain.model.ChartPoint
import com.devhjs.loge.domain.model.EmotionType
import com.devhjs.loge.domain.model.Stat
import com.devhjs.loge.domain.model.TilAnalysis
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class GetMonthlyDashboardUseCaseTest {

    private lateinit var getMonthlyStatUseCase: GetMonthlyStatUseCase
    private lateinit var getEmotionDistributionUseCase: GetEmotionDistributionUseCase
    private lateinit var getMonthlyReviewUseCase: GetMonthlyReviewUseCase
    private lateinit var getMonthlyDashboardUseCase: GetMonthlyDashboardUseCase

    private val month = "2024-02"
    private lateinit var mockStat: Stat
    private lateinit var mockEmotionMap: Map<EmotionType, Int>
    private lateinit var mockChartPoints: List<ChartPoint>
    private lateinit var mockAnalysis: TilAnalysis
    private lateinit var mockAiReport: AiReport

    @Before
    fun setUp() {
        getMonthlyStatUseCase = mockk()
        getEmotionDistributionUseCase = mockk()
        getMonthlyReviewUseCase = mockk()
        getMonthlyDashboardUseCase = GetMonthlyDashboardUseCase(
            getMonthlyStatUseCase,
            getEmotionDistributionUseCase,
            getMonthlyReviewUseCase
        )

        // Initialize common test data
        mockStat = Stat(
            date = month,
            totalTil = 10,
            avgEmotion = 80f,
            avgScore = 75f,
            avgDifficulty = 2.5f,
            emotionScoreList = emptyList(),
            learnedDates = emptyList()
        )
        mockEmotionMap = mapOf(EmotionType.FULFILLMENT to 5)
        mockChartPoints = listOf(ChartPoint(1f, 2f))
        mockAnalysis = TilAnalysis(mockEmotionMap, mockChartPoints)
        mockAiReport = AiReport(
            date = 123456789L,
            emotion = "성취감",
            emotionScore = 80,
            difficultyLevel = "보통",
            comment = "Good job"
        )
    }

    @Test
    fun `모든 데이터가 정상적으로 로드되면 DashboardModel을 반환해야 한다`() {
        runBlocking {
            // Given
            every { getMonthlyStatUseCase(month) } returns flowOf(mockStat)
            every { getEmotionDistributionUseCase(month) } returns flowOf(mockAnalysis)
            coEvery { getMonthlyReviewUseCase(month, false) } returns Result.Success(mockAiReport)

            // When
            val result = getMonthlyDashboardUseCase(month).first()

            // Then
            assert(result is Result.Success)
            val data = (result as Result.Success).data
            assertEquals(mockStat, data.stat)
            assertEquals(mockEmotionMap, data.emotionDistribution)
            assertEquals(mockChartPoints, data.difficultyChartPoints)
            assertEquals(mockAiReport, data.aiReport)
        }
    }

    @Test
    fun `AI 리포트가 실패하더라도 나머지 데이터는 정상적으로 반환해야 한다 (AI Report is null)`() {
        runBlocking {
            // Given
            val errorResult = Result.Error(Exception("Network Error"))
            // Analysis with empty map for this specific test case, overriding reusable mockAnalysis if needed, 
            // but actually we want to test that whatever is returned by usecase is what we get.
            // If we want to test empty map scenario specifically:
            val emptyAnalysis = TilAnalysis(emptyMap<EmotionType, Int>(), emptyList())
            
            every { getMonthlyStatUseCase(month) } returns flowOf(mockStat)
            every { getEmotionDistributionUseCase(month) } returns flowOf(emptyAnalysis)
            coEvery { getMonthlyReviewUseCase(month, false) } returns errorResult

            // When
            val result = getMonthlyDashboardUseCase(month).first()

            // Then
            assert(result is Result.Success)
            val data = (result as Result.Success).data
            assertEquals(mockStat, data.stat)
            assertEquals(emptyMap<EmotionType, Int>(), data.emotionDistribution)
            assertNull(data.aiReport)
        }
    }

    @Test
    fun `UseCase 중 하나라도 Exception을 던지면 Result_Error를 반환해야 한다`() {
        runBlocking {
            // Given
            val exception = RuntimeException("DB Error")

            every { getMonthlyStatUseCase(month) } returns flowOf(mockk())
            every { getEmotionDistributionUseCase(month) } returns flow { throw exception }
            coEvery { getMonthlyReviewUseCase(month, false) } returns Result.Success(mockk())

            // When
            val result = getMonthlyDashboardUseCase(month).first()

            // Then
            assert(result is Result.Error)
            assertEquals("DB Error", (result as Result.Error).error.message)
        }
    }
}
