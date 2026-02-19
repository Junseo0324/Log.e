package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.model.GithubProfile
import com.devhjs.loge.domain.model.User
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SyncGithubProfileUseCaseTest {

    private lateinit var getGithubProfileUseCase: GetGithubProfileUseCase
    private lateinit var getUserUseCase: GetUserUseCase
    private lateinit var saveUserUseCase: SaveUserUseCase
    private lateinit var syncGithubProfileUseCase: SyncGithubProfileUseCase

    @Before
    fun setUp() {
        getGithubProfileUseCase = mockk()
        getUserUseCase = mockk()
        saveUserUseCase = mockk()
        syncGithubProfileUseCase = SyncGithubProfileUseCase(
            getGithubProfileUseCase,
            getUserUseCase,
            saveUserUseCase
        )
    }

    @Test
    fun `Github 프로필 가져오기 성공 시 유저 정보를 업데이트하고 저장해야 한다`() = runBlocking {
        // Given
        val mockGithubProfile = GithubProfile(
            id = "github_id_123",
            name = "Github Name",
            avatarUrl = "http://avatar.url"
        )
        val mockUser = User(
            id = 1L,
            name = "Old Name",
            githubId = "temp_github_id",
            avatarUrl = null,
            isNotificationEnabled = false,
            isDarkModeEnabled = false,
            notificationTime = Pair(22, 0)
        )
        
        coEvery { getGithubProfileUseCase() } returns Result.Success(mockGithubProfile)
        coEvery { getUserUseCase() } returns flowOf(Result.Success(mockUser) as Result<User, Throwable>)
        coEvery { saveUserUseCase(any()) } returns Result.Success(Unit)

        // When
        val result = syncGithubProfileUseCase()

        // Then
        assertTrue(result is Result.Success)
        
        // Save 호출 검증
        io.mockk.coVerify { saveUserUseCase(match { 
            it.name == "Github Name" && 
            it.githubId == "github_id_123" &&
            it.avatarUrl == "http://avatar.url"
        }) }
    }

    @Test
    fun `Github 프로필 가져오기 실패 시 에러를 반환해야 한다`() = runBlocking {
        // Given
        val error = Exception("Network Error")
        coEvery { getGithubProfileUseCase() } returns Result.Error(error)

        // When
        val result = syncGithubProfileUseCase()

        // Then
        assertTrue(result is Result.Error)
        assertEquals(error, (result as Result.Error).error)
    }
}
