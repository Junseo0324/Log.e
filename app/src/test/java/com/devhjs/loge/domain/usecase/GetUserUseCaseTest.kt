package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.model.User
import com.devhjs.loge.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.lang.Exception

class GetUserUseCaseTest {

    private val repository: UserRepository = mockk()
    private val useCase = GetUserUseCase(repository)

    @Test
    fun `getUser 호출 시 레포지토리에서 데이터를 성공적으로 가져오는지 확인`() = runTest {
        // Given
        val expectedUser = User(
            name = "Test User",
            githubId = "testuser",
            isNotificationEnabled = true,
            isDarkModeEnabled = false,
            notificationTime = Pair(21, 0)
        )
        coEvery { repository.getUser() } returns flowOf(expectedUser)

        // When
        val result = useCase().toList()

        // Then
        assertEquals(1, result.size)
        assertTrue(result[0] is Result.Success)
        assertEquals(expectedUser, (result[0] as Result.Success).data)
        coVerify { repository.getUser() }
    }

    @Test
    fun `레포지토리에서 에러 발생 시 Error를 반환하는지 확인`() = runTest {
        // Given
        val exception = Exception("Load failed")
        coEvery { repository.getUser() } returns kotlinx.coroutines.flow.flow { throw exception }

        // When
        val result = useCase().toList()

        // Then
        assertEquals(1, result.size)
        assertTrue(result[0] is Result.Error)
        assertEquals(exception.message, (result[0] as Result.Error).error.message)
        coVerify { repository.getUser() }
    }
}
