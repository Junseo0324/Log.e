package com.devhjs.loge.domain.usecase

import com.devhjs.loge.domain.repository.AuthRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetSessionStatusUseCaseTest {

    private lateinit var authRepository: AuthRepository
    private lateinit var getSessionStatusUseCase: GetSessionStatusUseCase

    @Before
    fun setUp() {
        authRepository = mockk()
        getSessionStatusUseCase = GetSessionStatusUseCase(authRepository)
    }

    @Test
    fun `세션 상태 Flow를 올바르게 반환해야 한다 (로그인)`() = runBlocking {
        // Given
        val isLoggedIn = true
        every { authRepository.getSessionStatus() } returns flowOf(isLoggedIn)

        // When
        val result = getSessionStatusUseCase().first()

        // Then
        assertEquals(isLoggedIn, result)
    }

    @Test
    fun `세션 상태 Flow를 올바르게 반환해야 한다 (로그아웃)`() = runBlocking {
        // Given
        val isLoggedIn = false
        every { authRepository.getSessionStatus() } returns flowOf(isLoggedIn)

        // When
        val result = getSessionStatusUseCase().first()

        // Then
        assertEquals(isLoggedIn, result)
    }
}
