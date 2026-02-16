package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.repository.TilRepository
import com.devhjs.loge.domain.util.TimeProvider
import kotlinx.coroutines.flow.first
import java.time.ZoneId
import javax.inject.Inject

class GetRecentActivityDaysUseCase @Inject constructor(
    private val tilRepository: TilRepository,
    private val timeProvider: TimeProvider
) {
    /**
     * 최근 14일(오늘 포함) 동안 TIL을 작성한 총 일수를 반환합니다.
     */
    suspend operator fun invoke(): Result<Int, Throwable> {
        return try {
            val today = timeProvider.getCurrentDate()
            var activeDayCount = 0

            // 최근 14일 (오늘 포함, 과거 13일)
            for (i in 13 downTo 0) {
                val targetDate = today.minusDays(i.toLong())
                val start = targetDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
                val end = targetDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() - 1

                val tils = tilRepository.getAllTil(start, end).first()
                if (tils.isNotEmpty()) {
                    activeDayCount++
                }
            }
            Result.Success(activeDayCount)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
