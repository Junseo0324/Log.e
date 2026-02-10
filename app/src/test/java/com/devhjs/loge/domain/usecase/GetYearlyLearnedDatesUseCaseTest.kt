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

class GetYearlyLearnedDatesUseCaseTest {

    private lateinit var repository: TilRepository
    private lateinit var getYearlyLearnedDatesUseCase: GetYearlyLearnedDatesUseCase

    @Before
    fun setUp() {
        repository = mockk()
        getYearlyLearnedDatesUseCase = GetYearlyLearnedDatesUseCase(repository)
    }

    @Test
    fun `invoke 호출 시 중복 제거된 정렬된 날짜 목록을 반환하는지 확인`() = runBlocking {
        // Given
        val year = 2023
        val date1 = LocalDate.of(2023, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val date2 = LocalDate.of(2023, 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        // Duplicate date1 to test distinct
        val date3 = LocalDate.of(2023, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

        val tils = listOf(
            createTil(id = 1, createdAt = date1),
            createTil(id = 2, createdAt = date2),
            createTil(id = 3, createdAt = date3)
        )

        every { repository.getAllTil(any(), any()) } returns flowOf(tils)

        // When
        val result = getYearlyLearnedDatesUseCase(year).first()

        // Then
        assertEquals(2, result.size)
        assertEquals("2023-01-01", result[0])
        assertEquals("2023-12-31", result[1])
    }
    
    @Test
    fun `TIL이 없을 때 빈 리스트를 반환하는지 확인`() = runBlocking {
        // Given
        val year = 2023
        every { repository.getAllTil(any(), any()) } returns flowOf(emptyList())

        // When
        val result = getYearlyLearnedDatesUseCase(year).first()

        // Then
        assertEquals(0, result.size)
    }

    private fun createTil(
        id: Long,
        createdAt: Long
    ): Til {
        return Til(
            id = id,
            createdAt = createdAt,
            title = "Test Title",
            learned = "Test Learned",
            difficult = "Test Difficult",
            emotionScore = 5,
            emotion = EmotionType.SATISFACTION,
            difficultyLevel = 1,
            updatedAt = createdAt
        )
    }
}
