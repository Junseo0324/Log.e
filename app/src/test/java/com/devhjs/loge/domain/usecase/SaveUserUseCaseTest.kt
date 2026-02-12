package com.devhjs.loge.domain.usecase

import com.devhjs.loge.domain.model.User
import com.devhjs.loge.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SaveUserUseCaseTest {

    private val repository: UserRepository = mockk(relaxed = true)
    private val useCase = SaveUserUseCase(repository)

    @Test
    fun `saveUser 호출 시 레포지토리의 saveUser가 호출되는지 확인`() = runTest {
        // Given
        val user = User(
            name = "Test User",
            githubId = "testuser",
            isNotificationEnabled = true,
            isDarkModeEnabled = false
        )
        coEvery { repository.saveUser(user) } returns Unit

        // When
        useCase(user)

        // Then
        coVerify { repository.saveUser(user) }
    }
}
