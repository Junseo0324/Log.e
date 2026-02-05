package com.devhjs.loge.domain.usecase

import com.devhjs.loge.domain.model.Til
import com.devhjs.loge.domain.repository.TilRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetTilsUseCaseTest {

    private val repository: TilRepository = mockk()
    private val useCase = GetTilsUseCase(repository)

    @Test
    fun `repository에서_TIL_리스트를_가져와야_한다`() = runTest {
        // Given
        val start = 100L
        val end = 200L
        val mockTils = listOf(
            Til(1, 150, "Title", "Learned", "Difficult", 80, "Happy", 3, 150),
            Til(2, 160, "Title2", "Learned2", "Difficult2", 90, "Sad", 2, 160)
        )
        every { repository.getAllTil(start, end) } returns flowOf(mockTils)

        // When
        val result = useCase(start, end).first()

        // Then
        assertEquals(mockTils, result)
        verify { repository.getAllTil(start, end) }
    }
}
