package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.repository.AiRepository
import com.devhjs.loge.domain.repository.AuthRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class CheckDailyAnalysisAvailableUseCaseTest {

    private lateinit var aiRepository: AiRepository
    private lateinit var authRepository: AuthRepository
    private lateinit var checkDailyAnalysisAvailableUseCase: CheckDailyAnalysisAvailableUseCase

    @Before
    fun setUp() {
        aiRepository = mockk()
        authRepository = mockk()
        checkDailyAnalysisAvailableUseCase = CheckDailyAnalysisAvailableUseCase(
            aiRepository = aiRepository,
            authRepository = authRepository
        )
    }

    @Test
    fun `비로그인 상태일 때 false 를 반환한다`() = runTest {
        // Given
        coEvery { authRepository.getCurrentUserUid() } returns null

        // When
        val result = checkDailyAnalysisAvailableUseCase()

        // Then
        assertTrue(result is Result.Success)
        assertEquals(false, (result as Result.Success).data)
    }

    @Test
    fun `로그인 상태에서 오늘 사용 기록이 없으면 true 를 반환한다`() = runTest {
        // Given
        val userId = "user123"
        val today = LocalDate.now().toString()
        coEvery { authRepository.getCurrentUserUid() } returns userId
        coEvery { aiRepository.checkDailyAnalysisUsed(userId, today) } returns Result.Success(false)

        // When
        val result = checkDailyAnalysisAvailableUseCase()

        // Then
        assertTrue(result is Result.Success)
        assertEquals(true, (result as Result.Success).data)
    }

    @Test
    fun `로그인 상태에서 오늘 사용 기록이 있으면 false 를 반환한다`() = runTest {
        // Given
        val userId = "user123"
        val today = LocalDate.now().toString()
        coEvery { authRepository.getCurrentUserUid() } returns userId
        coEvery { aiRepository.checkDailyAnalysisUsed(userId, today) } returns Result.Success(true)

        // When
        val result = checkDailyAnalysisAvailableUseCase()

        // Then
        assertTrue(result is Result.Success)
        assertEquals(false, (result as Result.Success).data)
    }

    @Test
    fun `레포지토리에서 에러가 발생하면 Error 를 반환한다`() = runTest {
        // Given
        val userId = "user123"
        val today = LocalDate.now().toString()
        val exception = Exception("Network Error")
        coEvery { authRepository.getCurrentUserUid() } returns userId
        coEvery { aiRepository.checkDailyAnalysisUsed(userId, today) } returns Result.Error(exception)

        // When
        val result = checkDailyAnalysisAvailableUseCase()

        // Then
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).error)
    }
}
