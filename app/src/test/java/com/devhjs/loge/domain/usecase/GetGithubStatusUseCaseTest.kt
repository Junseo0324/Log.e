package com.devhjs.loge.domain.usecase

import com.devhjs.loge.domain.repository.AuthRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class GetGithubStatusUseCaseTest {

    private val authRepository: AuthRepository = mockk(relaxed = true)
    private val useCase = GetGithubStatusUseCase(authRepository)

    @Test
    fun `로그인 상태일 때 연결 정보가 포함된 GithubStatus 반환`() {
        val user = "TestUser"
        val avatarUrl = "https://avatars.githubusercontent.com/u/12345"
        // Given
        every { authRepository.isUserLoggedIn() } returns true
        every { authRepository.getGithubId() } returns user
        every { authRepository.getGithubAvatarUrl() } returns avatarUrl

        // When
        val status = useCase()

        // Then
        assertTrue(status.isConnected)
        assertEquals(user, status.username)
        assertEquals(avatarUrl, status.avatarUrl)
        verify { authRepository.isUserLoggedIn() }
        verify { authRepository.getGithubId() }
        verify { authRepository.getGithubAvatarUrl() }
    }

    @Test
    fun `비로그인 상태일 때 미연결 GithubStatus 반환`() {
        // Given
        every { authRepository.isUserLoggedIn() } returns false

        // When
        val status = useCase()

        // Then
        assertFalse(status.isConnected)
        assertNull(status.username)
        assertNull(status.avatarUrl)
        verify { authRepository.isUserLoggedIn() }
        verify(exactly = 0) { authRepository.getGithubId() }
        verify(exactly = 0) { authRepository.getGithubAvatarUrl() }
    }

    @Test
    fun `로그인 상태지만 GitHub 정보가 null인 경우`() {
        // Given
        every { authRepository.isUserLoggedIn() } returns true
        every { authRepository.getGithubId() } returns null
        every { authRepository.getGithubAvatarUrl() } returns null

        // When
        val status = useCase()

        // Then
        assertTrue(status.isConnected)
        assertNull(status.username)
        assertNull(status.avatarUrl)
    }
}
