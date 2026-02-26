package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.repository.TilRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SyncTilsUseCaseTest {

    private lateinit var tilRepository: TilRepository
    private lateinit var syncTilsUseCase: SyncTilsUseCase

    @Before
    fun setUp() {
        tilRepository = mockk(relaxed = true)
        syncTilsUseCase = SyncTilsUseCase(tilRepository)
    }

    @Test
    fun `TIL 동기화 시 원격 데이터를 먼저 가져오고 로컬 데이터를 업로드해야 한다`() = runBlocking {
        // When
        val result = syncTilsUseCase()

        // Then
        assertTrue(result is Result.Success)
        io.mockk.coVerifyOrder {
            tilRepository.fetchRemoteTilsToLocal()
            tilRepository.syncAllTilsToRemote()
        }
    }

    @Test
    fun `원격 데이터를 가져오는 중 에러가 발생하면 동기화가 중단되고 Error 결과를 반환해야 한다`() = runBlocking {
        // Given
        val exception = RuntimeException("Fetch Failed")
        coEvery { tilRepository.fetchRemoteTilsToLocal() } throws exception

        // When
        val result = syncTilsUseCase()

        // Then
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).error)
        coVerify(exactly = 0) { tilRepository.syncAllTilsToRemote() }
    }

    @Test
    fun `로컬 데이터를 업로드하는 중 에러가 발생하면 Error 결과를 반환해야 한다`() = runBlocking {
        // Given
        val exception = RuntimeException("Sync Failed")
        coEvery { tilRepository.syncAllTilsToRemote() } throws exception

        // When
        val result = syncTilsUseCase()

        // Then
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).error)
    }
}
