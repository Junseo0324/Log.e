package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.model.AiReport
import com.devhjs.loge.domain.model.EmotionType
import com.devhjs.loge.domain.model.Til
import com.devhjs.loge.domain.repository.AiRepository
import com.devhjs.loge.domain.repository.AuthRepository
import com.devhjs.loge.domain.repository.TilRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetMonthlyReviewUseCaseTest {

    private lateinit var aiRepository: AiRepository
    private lateinit var tilRepository: TilRepository
    private lateinit var authRepository: AuthRepository
    private lateinit var getMonthlyReviewUseCase: GetMonthlyReviewUseCase

    @Before
    fun setUp() {
        aiRepository = mockk(relaxed = true)
        tilRepository = mockk()
        authRepository = mockk()
        getMonthlyReviewUseCase = GetMonthlyReviewUseCase(aiRepository, tilRepository, authRepository)
    }

    @Test
    fun `데이터가 없을 때 분석 불가 메시지를 반환해야 한다`() = runBlocking {
        // Given
        val month = "2024-02"
        coEvery { aiRepository.getSavedMonthlyReview(any()) } returns Result.Success(null)
        coEvery { tilRepository.getAllTil(any(), any()) } returns flowOf(emptyList())

        // When
        val result = getMonthlyReviewUseCase(month)

        // Then
        assertTrue(result is Result.Success)
        val data = (result as Result.Success).data
        assertTrue(data != null)
        assertEquals("분석 불가", data?.emotion)
        assertEquals("이번 달에 작성된 회고가 없어 분석할 수 없습니다. 조금 더 꾸준히 기록해 보세요!", data?.comment)
    }

    @Test
    fun `데이터가 있을 때 AI 분석 결과를 반환해야 한다`() = runBlocking {
        // Given
        val month = "2024-02"
        val userId = "test-user-id"
        val tils = listOf(
            Til(
                id = 1,
                createdAt = 1706750000000,
                title = "Title 1",
                learned = "Learned 1",
                difficult = "Difficult 1",
                emotionScore = 80,
                emotion = EmotionType.FULFILLMENT,
                difficultyLevel = 1,
                updatedAt = 1706750000000
            ),
            Til(
                id = 2,
                createdAt = 1706850000000,
                title = "Title 2",
                learned = "Learned 2",
                difficult = "Difficult 2",
                emotionScore = 60,
                emotion = EmotionType.SATISFACTION,
                difficultyLevel = 2,
                updatedAt = 1706850000000
            )
        )
        val expectedReport = AiReport(
            date = System.currentTimeMillis(),
            emotion = "성취감",
            emotionScore = 70,
            difficultyLevel = "보통",
            comment = "Good job!"
        )

        coEvery { aiRepository.getSavedMonthlyReview(any()) } returns Result.Success(null)
        coEvery { tilRepository.getAllTil(any(), any()) } returns flowOf(tils)
        coEvery { aiRepository.getAiFeedback(any(), any(), any(), any()) } returns Result.Success(expectedReport)
        coEvery { authRepository.getCurrentUserUid() } returns userId
        coEvery { aiRepository.saveMonthlyReview(any(), any(), any()) } returns Result.Success(Unit)

        // When
        val result = getMonthlyReviewUseCase(month)

        // Then
        assertTrue(result is Result.Success)
        assertEquals(expectedReport, (result as Result.Success).data)
    }

    @Test
    fun `저장된 회고가 있으면 AI 분석 없이 반환해야 한다`() = runBlocking {
        // Given
        val month = "2024-02"
        val savedReport = AiReport(
            date = 1706750000000,
            emotion = "성취감",
            emotionScore = 90,
            difficultyLevel = "쉬움",
            comment = "Saved Comment"
        )
        
        coEvery { aiRepository.getSavedMonthlyReview(month) } returns Result.Success(savedReport)

        // When
        val result = getMonthlyReviewUseCase(month)

        // Then
        assertTrue(result is Result.Success)
        assertEquals(savedReport, (result as Result.Success).data)
    }

    @Test
    fun `forceFetchFromAi가 false이고 저장된 데이터가 없으면 null을 반환해야 한다`() = runBlocking {
        // Given
        val month = "2024-02"
        coEvery { aiRepository.getSavedMonthlyReview(any()) } returns Result.Success(null)

        // When
        val result = getMonthlyReviewUseCase(month, forceFetchFromAi = false)

        // Then
        assertTrue(result is Result.Success)
        assertEquals(null, (result as Result.Success).data)
    }

    @Test
    fun `repository Error 발생 시 에러 결과를 반환해야 한다`() = runBlocking {
        // Given
        val month = "2024-02"
        val exception = RuntimeException("DB Error")

        coEvery { aiRepository.getSavedMonthlyReview(any()) } returns Result.Success(null)
        coEvery { tilRepository.getAllTil(any(), any()) } throws exception

        // When
        val result = getMonthlyReviewUseCase(month)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).error)
    }
}
