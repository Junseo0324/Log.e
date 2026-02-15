package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.repository.AuthRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class SignInWithGithubUseCaseTest {

    private val authRepository: AuthRepository = mockk(relaxed = true)
    private val useCase = SignInWithGithubUseCase(authRepository)

    @Test
    fun `GitHub 로그인 성공 시 Result_Success 반환`() = runTest {
        // Given
        coEvery { authRepository.signInWithGithub() } returns Unit

        // When
        val result = useCase()

        // Then
        assertTrue(result is Result.Success)
        coVerify { authRepository.signInWithGithub() }
    }

    @Test
    fun `GitHub 로그인 실패 시 Result_Error 반환`() = runTest {
        // Given
        coEvery { authRepository.signInWithGithub() } throws RuntimeException("네트워크 오류")

        // When
        val result = useCase()

        // Then
        assertTrue(result is Result.Error)
        val error = (result as Result.Error).error
        assertTrue(error is RuntimeException)
        assertTrue(error.message == "네트워크 오류")
    }
}
