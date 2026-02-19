package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.model.GithubProfile
import com.devhjs.loge.domain.repository.AuthRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetGithubProfileUseCaseTest {

    private lateinit var authRepository: AuthRepository
    private lateinit var getGithubProfileUseCase: GetGithubProfileUseCase

    @Before
    fun setUp() {
        authRepository = mockk()
        getGithubProfileUseCase = GetGithubProfileUseCase(authRepository)
    }

    @Test
    fun `성공적으로 Github 프로필을 가져와야 한다`() {
        // Given
        val name = "testUser"
        val id = "12345"
        val avatarUrl = "http://example.com/avatar.png"
        
        every { authRepository.getGithubName() } returns name
        every { authRepository.getGithubId() } returns id
        every { authRepository.getGithubAvatarUrl() } returns avatarUrl

        // When
        val result = getGithubProfileUseCase()

        // Then
        assertTrue(result is Result.Success)
        val data = (result as Result.Success).data
        assertEquals(name, data.name)
        assertEquals(id, data.id)
        assertEquals(avatarUrl, data.avatarUrl)
    }

    @Test
    fun `프로필 가져오기 중 예외 발생 시 에러를 반환해야 한다`() {
        // Given
        val exception = RuntimeException("Network Error")
        every { authRepository.getGithubName() } throws exception

        // When
        val result = getGithubProfileUseCase()

        // Then
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).error)
    }
}
