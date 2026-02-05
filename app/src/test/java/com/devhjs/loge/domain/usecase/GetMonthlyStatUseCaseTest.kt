package com.devhjs.loge.domain.usecase

import com.devhjs.loge.domain.model.ChartPoint
import com.devhjs.loge.domain.model.Stat
import com.devhjs.loge.domain.repository.TilRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetMonthlyStatUseCaseTest {

    private val repository: TilRepository = mockk()
    private val useCase = GetMonthlyStatUseCase(repository)

    @Test
    fun `repository에서_통계_데이터를_가져와야_한다`() = runTest {
        // Given
        val month = "2024-02"
        val mockStat = Stat(
            date = month,
            totalTil = 10,
            avgEmotion = 4.5f,
            avgScore = 85f,
            avgDifficulty = 3.0f,
            emotionScoreList = listOf(ChartPoint(1f, 80f), ChartPoint(2f, 90f)),
            learnedDates = listOf("2024-02-01", "2024-02-02"),
            aiReport = null
        )
        every { repository.getMonthlyStats(month) } returns flowOf(mockStat)

        // When
        val result = useCase(month).first()

        // Then
        assertEquals(mockStat, result)
        verify { repository.getMonthlyStats(month) }
    }
}
