package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.model.Feedback
import com.devhjs.loge.domain.model.FeedbackType
import com.devhjs.loge.domain.repository.FeedbackRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SendFeedbackUseCaseTest {

    private val repository: FeedbackRepository = mockk(relaxed = true)
    private val useCase = SendFeedbackUseCase(repository)

    private val testFeedback = Feedback(
        type = FeedbackType.BUG,
        title = "테스트 제목",
        content = "테스트 내용"
    )

    @Test
    fun `피드백 전송 성공 시 Success 반환`() = runTest {
        // Given
        coEvery { repository.sendFeedback(testFeedback) } returns Unit

        // When
        val result = useCase(testFeedback)

        // Then
        assertTrue(result is Result.Success)
        coVerify(exactly = 1) { repository.sendFeedback(testFeedback) }
    }

    @Test
    fun `피드백 전송 실패 시 Error 반환`() = runTest {
        // Given
        val exception = RuntimeException("네트워크 오류")
        coEvery { repository.sendFeedback(testFeedback) } throws exception

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
        coEvery { repository.sendFeedback(featureFeedback) } returns Unit

        // When
        val result = useCase(featureFeedback)

        // Then
        assertTrue(result is Result.Success)
        coVerify { repository.sendFeedback(featureFeedback) }
    }

    @Test
    fun `OTHER 타입 피드백 전송 성공`() = runTest {
        // Given
        val otherFeedback = Feedback(
            type = FeedbackType.OTHER,
            title = "기타 의견",
            content = "기타 피드백 내용입니다"
        )
        coEvery { repository.sendFeedback(otherFeedback) } returns Unit

        // When
        val result = useCase(otherFeedback)

        // Then
        assertTrue(result is Result.Success)
        coVerify { repository.sendFeedback(otherFeedback) }
    }
}
