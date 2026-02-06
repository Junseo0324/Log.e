package com.devhjs.loge.domain.usecase

import com.devhjs.loge.domain.model.EmotionType
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

class GetTilUseCaseTest {

    private val repository: TilRepository = mockk()
    private val useCase = GetTilUseCase(repository)

    @Test
    fun `repository에서_TIL을_가져와야_한다`() = runTest {
        // Given
        val id = 1L
        val mockTil = Til(id, 150, "Title", "Learned", "Difficult", 80, EmotionType.SATISFACTION, 3, 150)
        every { repository.getTil(id) } returns flowOf(mockTil)

        // When
        val result = useCase(id).first()

        // Then
        assertEquals(mockTil, result)
        verify { repository.getTil(id) }
    }
}
