package com.devhjs.loge.domain.usecase

import com.devhjs.loge.domain.network.ConnectivityManager
import com.devhjs.loge.domain.repository.AuthRepository
import com.devhjs.loge.domain.repository.TilRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SyncOnReconnectUseCaseTest {

    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var tilRepository: TilRepository
    private lateinit var authRepository: AuthRepository
    private lateinit var syncOnReconnectUseCase: SyncOnReconnectUseCase

    @Before
    fun setUp() {
        connectivityManager = mockk()
        tilRepository = mockk(relaxed = true)
        authRepository = mockk()
        syncOnReconnectUseCase = SyncOnReconnectUseCase(
            connectivityManager,
            tilRepository,
            authRepository
        )
    }

    @Test
    fun `네트워크가 복구되고(false-true) 로그인 상태이면 동기화를 수행해야 한다`() = runBlocking {
        // Given
        every { connectivityManager.observe() } returns flowOf(false, true)
        coEvery { authRepository.isUserLoggedIn() } returns true

        // When
        val result = syncOnReconnectUseCase().toList()

        // Then
        assertEquals(1, result.size) // 한 번 동기화 이벤트 발생
        io.mockk.coVerifyOrder {
            tilRepository.fetchRemoteTilsToLocal()
            tilRepository.syncAllTilsToRemote()
        }
    }

    @Test
    fun `네트워크가 복구되었으나 로그인 상태가 아니면 동기화를 수행하지 않아야 한다`() = runBlocking {
        // Given
        every { connectivityManager.observe() } returns flowOf(false, true)
        coEvery { authRepository.isUserLoggedIn() } returns false

        // When
        val result = syncOnReconnectUseCase().toList()

        // Then
        assertEquals(1, result.size) // 이벤트는 발생하지만
        coVerify(exactly = 0) {
            tilRepository.fetchRemoteTilsToLocal()
            tilRepository.syncAllTilsToRemote()
        }
    }

    @Test
    fun `네트워크 연결이 계속 유지된 경우(true) 초기 1회 외에 중복 동기화를 수행하지 않아야 한다`() = runBlocking {
        // Given
        every { connectivityManager.observe() } returns flowOf(true, true, true)
        coEvery { authRepository.isUserLoggedIn() } returns true

        // When
        val result = syncOnReconnectUseCase().toList()

        // Then
        assertEquals(1, result.size) // 초기 (false->true) 감지 1회만 발생
        coVerify(exactly = 1) {
            tilRepository.fetchRemoteTilsToLocal()
            tilRepository.syncAllTilsToRemote()
        }
    }
}
