package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.DateUtils
import com.devhjs.loge.domain.model.EmotionType
import com.devhjs.loge.domain.model.Til
import com.devhjs.loge.domain.repository.TilRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetTodayLogUseCaseTest {

    private lateinit var repository: TilRepository
    private lateinit var getTodayLogUseCase: GetTodayLogUseCase

    @Before
    fun setUp() {
        repository = mockk()
        getTodayLogUseCase = GetTodayLogUseCase(repository)
        mockkObject(DateUtils)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `오늘 작성된 로그가 있을 경우 해당 Til 객체를 반환하는지 확인`() = runTest {
        // Given
        val start = 1000L
        val end = 2000L
        val til = Til(
            id = 1,
            title = "Test Log",
            createdAt = 1500L,
            learned = "Test",
            difficult = "Test",
            emotionScore = 1,
            emotion = EmotionType.SATISFACTION,
            difficultyLevel = 1,
            updatedAt = 1500L
        )

        every { DateUtils.getTodayStartEnd() } returns Pair(start, end)
        every { repository.getAllTil(start, end) } returns flowOf(listOf(til))

        // When
        val result = getTodayLogUseCase().first()

        // Then
        assertEquals(til, result)
    }

    @Test
    fun `오늘 작성된 로그가 없을 경우 null을 반환하는지 확인`() = runTest {
        // Given
        val start = 1000L
        val end = 2000L

        every { DateUtils.getTodayStartEnd() } returns Pair(start, end)
        every { repository.getAllTil(start, end) } returns flowOf(emptyList())

        // When
        val result = getTodayLogUseCase().first()

        // Then
        assertEquals(null, result)
    }
}
