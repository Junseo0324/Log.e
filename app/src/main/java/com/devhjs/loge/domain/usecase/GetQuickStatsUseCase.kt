package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.DateUtils
import com.devhjs.loge.domain.model.QuickStats
import com.devhjs.loge.domain.repository.TilRepository
import com.devhjs.loge.domain.util.TimeProvider
import kotlinx.coroutines.flow.first
import java.time.ZoneId
import javax.inject.Inject

class GetQuickStatsUseCase @Inject constructor(
    private val tilRepository: TilRepository,
    private val timeProvider: TimeProvider
) {
    suspend operator fun invoke(): QuickStats {
        // 전체 TIL 수 가져오기
        val allTils = tilRepository.getAllTils()
        val totalCount = allTils.size

        val currentDate = timeProvider.getCurrentDate()
        val timestamp = currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

        // 이번달 통계 가져오기 (YYYY-MM 형식)
        val currentMonth = DateUtils.formatToYearMonth(timestamp)
        val monthlyStats = tilRepository.getMonthlyStats(currentMonth).first()

        val monthlyCount = monthlyStats.totalTil
        val avgDifficulty = monthlyStats.avgDifficulty

        // 달성률 계산: 이번달 학습한 날 수 / 이번달 경과 일 수
        val dayOfMonth = DateUtils.getDayOfMonth(timestamp)
        val learnedDays = monthlyStats.learnedDates.size
        val achievementRate = if (dayOfMonth > 0) {
            ((learnedDays.toFloat() / dayOfMonth) * 100).toInt()
        } else 0

        return QuickStats(
            totalCount = totalCount,
            monthlyCount = monthlyCount,
            avgDifficulty = avgDifficulty,
            achievementRate = achievementRate
        )
    }
}
