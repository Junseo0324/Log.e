package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.repository.AuthRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class SignOutGithubUseCaseTest {

    private val authRepository: AuthRepository = mockk(relaxed = true)
    private val useCase = SignOutGithubUseCase(authRepository)

    @Test
    fun `GitHub 로그아웃 성공 시 Result_Success 반환`() = runTest {
        // Given
        coEvery { authRepository.signOut() } returns Unit

        // When
        val result = useCase()

        // Then
        assertTrue(result is Result.Success)
        coVerify { authRepository.signOut() }
    }

    @Test
    fun `GitHub 로그아웃 실패 시 Result_Error 반환`() = runTest {
        // Given
        coEvery { authRepository.signOut() } throws RuntimeException("로그아웃 실패")

        // When
        val result = useCase()

        // Then
        assertTrue(result is Result.Error)
        val error = (result as Result.Error).error
        assertTrue(error.message == "로그아웃 실패")
    }
}
