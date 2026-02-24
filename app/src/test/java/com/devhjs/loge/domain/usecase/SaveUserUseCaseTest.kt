package com.devhjs.loge.domain.usecase

import com.devhjs.loge.domain.model.User
import com.devhjs.loge.domain.repository.AuthRepository
import com.devhjs.loge.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SaveUserUseCaseTest {

    private val userRepository: UserRepository = mockk(relaxed = true)
    private val authRepository: AuthRepository = mockk(relaxed = true)
    private lateinit var useCase: SaveUserUseCase

    @Before
    fun setUp() {
        useCase = SaveUserUseCase(userRepository, authRepository)
        coEvery { authRepository.isUserLoggedIn() } returns true
        coEvery { authRepository.getCurrentUserUid() } returns "test_user_id"
    }

    @Test
    fun `saveUser 호출 시 레포지토리의 saveUser가 호출되는지 확인`() = runTest {
        // Given
        val user = User(
            name = "Test User",
            githubId = "testuser",
            isNotificationEnabled = true,
            isDarkModeEnabled = false,
            notificationTime = Pair(21, 0)
        )
        coEvery { userRepository.saveUser(user, any()) } returns Unit

        // When
        useCase(user)

        // Then
        coVerify { userRepository.saveUser(user, "test_user_id") }
    }
}
