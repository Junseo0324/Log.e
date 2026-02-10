package com.devhjs.loge.domain.usecase

import com.devhjs.loge.domain.model.EmotionType
import com.devhjs.loge.domain.model.Til
import com.devhjs.loge.domain.repository.TilRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.ZoneId

class GetEmotionDistributionUseCaseTest {

    private lateinit var repository: TilRepository
    private lateinit var getEmotionDistributionUseCase: GetEmotionDistributionUseCase

    private fun createTil(
        id: Long,
        createdAt: Long,
        emotion: EmotionType,
        difficultyLevel: Int
    ): Til {
        return Til(
            id = id,
            createdAt = createdAt,
            title = "Test Title",
            learned = "Test Learned",
            difficult = "Test Difficult",
            emotionScore = 5,
            emotion = emotion,
            difficultyLevel = difficultyLevel,
            updatedAt = createdAt
        )
    }

    @Before
    fun setUp() {
        repository = mockk()
        getEmotionDistributionUseCase = GetEmotionDistributionUseCase(repository)
    }

    @Test
    fun `invoke 호출 시 올바른 감정 분포와 난이도 차트 포인트를 반환하는지 확인`() = runBlocking {
        // Given
        val month = "2023-10"
        val date1 = LocalDate.of(2023, 10, 1).atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000
        val date2 = LocalDate.of(2023, 10, 5).atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000
        val date3 = LocalDate.of(2023, 10, 10).atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000

        val tils = listOf(
            createTil(id = 1, createdAt = date1, emotion = EmotionType.SATISFACTION, difficultyLevel = 3),
            createTil(id = 2, createdAt = date2, emotion = EmotionType.FRUSTRATION, difficultyLevel = 5),
            createTil(id = 3, createdAt = date3, emotion = EmotionType.SATISFACTION, difficultyLevel = 2)
        )

        every { repository.getAllTil(any(), any()) } returns flowOf(tils)

        // When
        val result = getEmotionDistributionUseCase(month).first()

        // Then
        assertEquals(2, result.emotionDistribution[EmotionType.SATISFACTION])
        assertEquals(1, result.emotionDistribution[EmotionType.FRUSTRATION])

        assertEquals(3, result.difficultyChartPoints.size)
        
        assertEquals(1f, result.difficultyChartPoints[0].x)
        assertEquals(3f, result.difficultyChartPoints[0].y)

        assertEquals(5f, result.difficultyChartPoints[1].x)
        assertEquals(5f, result.difficultyChartPoints[1].y)

        assertEquals(10f, result.difficultyChartPoints[2].x)
        assertEquals(2f, result.difficultyChartPoints[2].y)
    }

    @Test
    fun `TIL이 없을 때 빈 분석 결과를 반환하는지 확인`() = runBlocking {
        // Given
        val month = "2023-10"
        every { repository.getAllTil(any(), any()) } returns flowOf(emptyList())

        // When
        val result = getEmotionDistributionUseCase(month).first()

        // Then
        assertEquals(0, result.emotionDistribution.size)
        assertEquals(0, result.difficultyChartPoints.size)
    }
}
