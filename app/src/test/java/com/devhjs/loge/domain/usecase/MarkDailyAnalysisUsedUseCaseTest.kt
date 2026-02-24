package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.repository.AiRepository
import com.devhjs.loge.domain.repository.AuthRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class MarkDailyAnalysisUsedUseCaseTest {

    private lateinit var aiRepository: AiRepository
    private lateinit var authRepository: AuthRepository
    private lateinit var markDailyAnalysisUsedUseCase: MarkDailyAnalysisUsedUseCase

    @Before
    fun setUp() {
        aiRepository = mockk()
        authRepository = mockk()
        markDailyAnalysisUsedUseCase = MarkDailyAnalysisUsedUseCase(
            aiRepository = aiRepository,
            authRepository = authRepository
        )
    }

    @Test
    fun `비로그인 상태일 때 기록을 생략하고 Success(Unit) 을 반환한다`() = runTest {
        // Given
        coEvery { authRepository.getCurrentUserUid() } returns null

        // When
        val result = markDailyAnalysisUsedUseCase()

        // Then
        assertTrue(result is Result.Success)
        assertEquals(Unit, (result as Result.Success).data)
        
        // 레포지토리 함수가 전혀 호출되지 않아야 함
        coVerify(exactly = 0) { aiRepository.markDailyAnalysisUsed(any(), any()) }
    }

    @Test
    fun `로그인 상태일 때 markDailyAnalysisUsed 를 정상적으로 수행하고 결과를 반환한다`() = runTest {
        // Given
        val userId = "user123"
        val today = LocalDate.now().toString()
        coEvery { authRepository.getCurrentUserUid() } returns userId
        coEvery { aiRepository.markDailyAnalysisUsed(userId, today) } returns Result.Success(Unit)

        // When
        val result = markDailyAnalysisUsedUseCase()

        // Then
        assertTrue(result is Result.Success)
        assertEquals(Unit, (result as Result.Success).data)
        
        coVerify(exactly = 1) { aiRepository.markDailyAnalysisUsed(userId, today) }
    }
    
    @Test
    fun `로그인 상태에서 레포지토리 오류 발생 시 Error 를 반환한다`() = runTest {
        // Given
        val userId = "user123"
        val today = LocalDate.now().toString()
        val exception = Exception("Network Error")
        coEvery { authRepository.getCurrentUserUid() } returns userId
        coEvery { aiRepository.markDailyAnalysisUsed(userId, today) } returns Result.Error(exception)

        // When
        val result = markDailyAnalysisUsedUseCase()

        // Then
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).error)
    }
}
