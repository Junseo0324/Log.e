package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.model.EmotionType
import com.devhjs.loge.domain.model.Til
import com.devhjs.loge.domain.repository.FileRepository
import com.devhjs.loge.domain.repository.TilRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.lang.Exception

class ExportTilUseCaseTest {

    private val tilRepository: TilRepository = mockk()
    private val fileRepository: FileRepository = mockk()
    private val useCase = ExportTilUseCase(tilRepository, fileRepository)

    @Test
    fun `데이터가 없을 때 Error를 반환하는지 확인`() = runTest {
        // Given
        coEvery { tilRepository.getAllTils() } returns emptyList()

        // When
        val result = useCase("content://uri")

        // Then
        assertTrue(result is Result.Error)
        assertEquals("내보낼 데이터가 없습니다.", (result as Result.Error).error.message)
    }

    @Test
    fun `데이터가 있을 때 CSV 포맷으로 변환하여 저장하는지 확인`() = runTest {
        // Given
        val til1 = Til(
            id = 1,
            createdAt = 1707350400000L, // 2024-02-08 09:00:00 (approx)
            title = "Title 1",
            learned = "Learned 1",
            difficult = "Diff 1",
            emotionScore = 80,
            emotion = EmotionType.SATISFACTION, 
            difficultyLevel = 3,
            updatedAt = 1707350400000L
        )
        // Adjust for DateUtils format. Assuming convertToIsoDate uses default locale or consistent format.
        // Actually, without mocking DateUtils or knowing its implementation, exact string match is hard.
        // But we can check if fileRepository.saveToUri is called.
        
        coEvery { tilRepository.getAllTils() } returns listOf(til1)
        coEvery { fileRepository.saveToUri(any(), any()) } returns Unit

        // When
        val result = useCase("content://uri")

        // Then
        assertTrue(result is Result.Success)
        
        val contentSlot = slot<String>()
        coVerify { fileRepository.saveToUri(eq("content://uri"), capture(contentSlot)) }
        
        val content = contentSlot.captured
        assertTrue(content.startsWith("날짜,제목,감정,난이도,학습 내용,추가 학습,시간\n"))
        assertTrue(content.contains("Title 1"))
        assertTrue(content.contains("Learned 1"))
    }
}
