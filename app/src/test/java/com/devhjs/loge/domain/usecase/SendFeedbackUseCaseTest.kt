package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.model.Feedback
import com.devhjs.loge.domain.model.FeedbackType
import com.devhjs.loge.domain.repository.AuthRepository
import com.devhjs.loge.domain.repository.FeedbackRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SendFeedbackUseCaseTest {

    private val feedbackRepository: FeedbackRepository = mockk(relaxed = true)
    private val authRepository: AuthRepository = mockk(relaxed = true)
    private lateinit var useCase: SendFeedbackUseCase

    @Before
    fun setUp() {
        useCase = SendFeedbackUseCase(feedbackRepository, authRepository)
        coEvery { authRepository.isUserLoggedIn() } returns true
        coEvery { authRepository.getCurrentUserUid() } returns "test_user_id"
    }

    private val testFeedback = Feedback(
        type = FeedbackType.BUG,
        title = "테스트 제목",
        content = "테스트 내용"
    )

    @Test
    fun `피드백 전송 성공 시 Success 반환`() = runTest {
        // Given
        coEvery { feedbackRepository.sendFeedback(testFeedback, any()) } returns Unit

        // When
        val result = useCase(testFeedback)

        // Then
        assertTrue(result is Result.Success)
        coVerify(exactly = 1) { feedbackRepository.sendFeedback(testFeedback, "test_user_id") }
    }

    @Test
    fun `피드백 전송 실패 시 Error 반환`() = runTest {
        // Given
        val exception = RuntimeException("네트워크 오류")
        coEvery { feedbackRepository.sendFeedback(testFeedback, any()) } throws exception

        // When
        val result = useCase(testFeedback)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).error)
    }

    @Test
    fun `FEATURE 타입 피드백 전송 성공`() = runTest {
        // Given
        val featureFeedback = Feedback(
            type = FeedbackType.FEATURE,
            title = "기능 요청",
            content = "새로운 기능을 추가해주세요"
        )
        coEvery { feedbackRepository.sendFeedback(featureFeedback, any()) } returns Unit

        // When
        val result = useCase(featureFeedback)

        // Then
        assertTrue(result is Result.Success)
        coVerify { feedbackRepository.sendFeedback(featureFeedback, "test_user_id") }
    }

    @Test
    fun `OTHER 타입 피드백 전송 성공`() = runTest {
        // Given
        val otherFeedback = Feedback(
            type = FeedbackType.OTHER,
            title = "기타 의견",
            content = "기타 피드백 내용입니다"
        )
        coEvery { feedbackRepository.sendFeedback(otherFeedback, any()) } returns Unit

        // When
        val result = useCase(otherFeedback)

        // Then
        assertTrue(result is Result.Success)
        coVerify { feedbackRepository.sendFeedback(otherFeedback, "test_user_id") }
    }
}
