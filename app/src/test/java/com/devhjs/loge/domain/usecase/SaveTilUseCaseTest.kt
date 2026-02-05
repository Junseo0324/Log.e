package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.model.Til
import com.devhjs.loge.domain.repository.TilRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SaveTilUseCaseTest {

    private val repository: TilRepository = mockk()
    private val useCase = SaveTilUseCase(repository)

    @Test
    fun `repository_호출이_성공하면_Success를_반환해야_한다`() = runTest {
        // Given
        val til = Til(1, 150, "Title", "Learned", "Difficult", 80, "Happy", 3, 150)
        coEvery { repository.saveTil(til) } returns Unit

        // When
        val result = useCase(til)

        // Then
        assertTrue(result is Result.Success)
        coVerify { repository.saveTil(til) }
    }

    @Test
    fun `repository에서_예외가_발생하면_Error를_반환해야_한다`() = runTest {
        // Given
        val til = Til(1, 150, "Title", "Learned", "Difficult", 80, "Happy", 3, 150)
        val exception = RuntimeException("Database error")
        coEvery { repository.saveTil(til) } throws exception

        // When
        val result = useCase(til)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).error)
        coVerify { repository.saveTil(til) }
    }
}
