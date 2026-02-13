package com.devhjs.loge.domain.usecase

import com.devhjs.loge.domain.model.Til
import com.devhjs.loge.domain.repository.TilRepository
import com.devhjs.loge.domain.util.TimeProvider
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate
import java.time.ZoneId

class GetWeeklyTilCountUseCaseTest {

    private val tilRepository: TilRepository = mockk()
    private val timeProvider: TimeProvider = mockk()
    private val useCase = GetWeeklyTilCountUseCase(tilRepository, timeProvider)

    @Test
    fun `invoke returns correct weekly stats given a specific date`() = runBlocking {
        val fixedDate = LocalDate.of(2026, 2, 13) // 금요일
        coEvery { timeProvider.getCurrentDate() } returns fixedDate

        val til1 = Til(
            id = 1,
            createdAt = LocalDate.of(2026, 2, 8).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
            title = "",
            learned = "",
            difficult = "",
            emotionScore = 0,
            emotion = com.devhjs.loge.domain.model.EmotionType.NORMAL,
            difficultyLevel = 0,
            updatedAt = 0
        )
        val til2 = Til(
            id = 2,
            createdAt = LocalDate.of(2026, 2, 10).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
            title = "",
            learned = "",
            difficult = "",
            emotionScore = 0,
            emotion = com.devhjs.loge.domain.model.EmotionType.NORMAL,
            difficultyLevel = 0,
            updatedAt = 0
        )
        
        coEvery { tilRepository.getAllTil(any(), any()) } returns flowOf(listOf(til1, til2))

        // When
        val result = useCase().first()

        // Then
        // Then
        assertEquals(2, result)
    }
}
