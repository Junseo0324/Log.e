package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.model.AiReport
import com.devhjs.loge.domain.repository.AiRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AnalyzeLogUseCaseTest {

    private val repository: AiRepository = mockk()
    private val useCase = AnalyzeLogUseCase(repository)

    @Test
    fun `repository의 분석이 Success를 반환하는지 확인`() = runTest {
        // Given
        val title = "Test Title"
        val learned = "Test Learned"
        val difficult = "Test Difficult"
        val expectedReport = AiReport(
            date = 1234567890L,
            emotion = "성취감",
            emotionScore = 90,
            difficultyLevel = "보통",
            comment = "Test Comment"
        )
        coEvery { repository.analyzeLog(title, learned, difficult) } returns Result.Success(expectedReport)

        // When
        val result = useCase(title, learned, difficult)

        // Then
        assertTrue(result is Result.Success)
        assertEquals(expectedReport, (result as Result.Success).data)
        coVerify { repository.analyzeLog(title, learned, difficult) }
    }

    @Test
    fun `repository의 분석이 Error를 반환하는지 확인`() = runTest {
        // Given
        val title = "Test Title"
        val learned = "Test Learned"
        val difficult = "Test Difficult"
        val expectedError = RuntimeException("API Error")
        coEvery { repository.analyzeLog(title, learned, difficult) } returns Result.Error(expectedError)

        // When
        val result = useCase(title, learned, difficult)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(expectedError, (result as Result.Error).error)
        coVerify { repository.analyzeLog(title, learned, difficult) }
    }
}
