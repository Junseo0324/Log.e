package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.DateUtils
import com.devhjs.loge.domain.model.WeeklyStats
import com.devhjs.loge.domain.repository.TilRepository
import com.devhjs.loge.domain.util.TimeProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetWeeklyTilCountUseCase @Inject constructor(
    private val tilRepository: TilRepository,
    private val timeProvider: TimeProvider
) {
    operator fun invoke(): Flow<WeeklyStats> {
        val now = timeProvider.getCurrentDate()
        val (startTimestamp, endTimestamp) = DateUtils.getWeekStartEnd(now)
        
        val startOfWeek = DateUtils.toLocalDate(startTimestamp)
        val endOfWeek = DateUtils.toLocalDate(endTimestamp)

        return tilRepository.getAllTil(startTimestamp, endTimestamp).map { tils ->
            val totalCount = tils.size
            
            val weekArray = BooleanArray(7) { false }
            
            tils.forEach { til ->
                val tilDate = DateUtils.toLocalDate(til.createdAt)
                
                // TIL 날짜가 이번 주 범위 내에 있는지 확인
                if (!tilDate.isBefore(startOfWeek) && !tilDate.isAfter(endOfWeek)) {
                    val index = DateUtils.getDayIndex(tilDate)
                    if (index in 0..6) {
                        weekArray[index] = true
                    }
                }
            }
            
            WeeklyStats(
                totalCount = totalCount,
                dailyActivity = weekArray.toList()
            )
        }
    }
}
