package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.model.EmotionType
import com.devhjs.loge.domain.model.Til
import com.devhjs.loge.domain.repository.TilRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

import com.devhjs.loge.domain.util.WidgetUpdateManager

class DeleteTilUseCaseTest {

    private val repository: TilRepository = mockk()
    private val widgetUpdateManager: WidgetUpdateManager = mockk(relaxed = true)
    private val useCase = DeleteTilUseCase(repository, widgetUpdateManager)

    @Test
    fun `repository_호출이_성공하면_Success를_반환해야_한다`() = runTest {
        // Given
        val til = Til(1, 150, "Title", "Learned", "Difficult", 80, EmotionType.SATISFACTION, 3, 150)
        coEvery { repository.deleteTil(til.id) } returns Unit

        // When
        val result = useCase(til.id)

        // Then
        assertTrue(result is Result.Success)
        coVerify { repository.deleteTil(til.id) }
    }

    @Test
    fun `repository에서_예외가_발생하면_Error를_반환해야_한다`() = runTest {
        // Given
        val til = Til(1, 150, "Title", "Learned", "Difficult", 80, EmotionType.SATISFACTION, 3, 150)
        val exception = RuntimeException("Delete error")
        coEvery { repository.deleteTil(til.id) } throws exception

        // When
        val result = useCase(til.id)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).error)
        coVerify { repository.deleteTil(til.id) }
    }
}
