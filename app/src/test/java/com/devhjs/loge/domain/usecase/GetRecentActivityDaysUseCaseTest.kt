package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.model.Til
import com.devhjs.loge.domain.repository.TilRepository
import com.devhjs.loge.domain.util.TimeProvider
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.ZoneId

class GetRecentActivityDaysUseCaseTest {

    private lateinit var tilRepository: TilRepository
    private lateinit var timeProvider: TimeProvider
    private lateinit var getRecentActivityDaysUseCase: GetRecentActivityDaysUseCase

    @Before
    fun setUp() {
        tilRepository = mockk()
        timeProvider = mockk()
        getRecentActivityDaysUseCase = GetRecentActivityDaysUseCase(tilRepository, timeProvider)
    }

    @Test
    fun `최근 14일 동안 TIL이 작성된 날짜 수를 정확히 반환해야 한다`() = runBlocking {
        // Given
        val today = LocalDate.of(2024, 2, 14) // 기준 날짜 설정
        every { timeProvider.getCurrentDate() } returns today

        // 기본적으로 비어있는 리스트 반환
        coEvery { tilRepository.getAllTil(any(), any()) } returns flowOf(emptyList())

        // TIL이 있는 날짜에 대해 mock 설정
        val daysWithTil = listOf(0L, 2L, 13L) // 0일전(오늘), 2일전, 13일전
        
        daysWithTil.forEach { daysAgo ->
            val targetDate = today.minusDays(daysAgo)
            val start = targetDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            val end = targetDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() - 1
            
            coEvery { tilRepository.getAllTil(start, end) } returns flowOf(listOf(mockk<Til>()))
        }

        // When
        val result = getRecentActivityDaysUseCase()

        // Then
        assertTrue(result is Result.Success)
        assertEquals(3, (result as Result.Success).data)
    }

    @Test
    fun `TIL 데이터 조회 중 예외 발생 시 에러를 반환해야 한다`() = runBlocking {
        // Given
        val today = LocalDate.of(2024, 2, 14)
        val exception = RuntimeException("DB Error")
        
        every { timeProvider.getCurrentDate() } returns today
        coEvery { tilRepository.getAllTil(any(), any()) } throws exception

        // When
        val result = getRecentActivityDaysUseCase()

        // Then
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).error)
    }
}
