package com.devhjs.loge.data.worker

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.devhjs.loge.core.util.DateUtils
import com.devhjs.loge.domain.repository.TilRepository
import com.devhjs.loge.domain.util.TimeProvider
import com.devhjs.loge.presentation.widget.QuickStatsWidget
import com.devhjs.loge.presentation.widget.QuickStatsWidgetKeys
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import timber.log.Timber
import java.time.ZoneId

@HiltWorker
class QuickStatsWidgetWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val tilRepository: TilRepository,
    private val timeProvider: TimeProvider
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
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

            // Glance 위젯 상태 업데이트
            val manager = GlanceAppWidgetManager(context)
            val glanceIds = manager.getGlanceIds(QuickStatsWidget::class.java)

            Timber.d("QuickStatsWidgetWorker: Found ${glanceIds.size} widgets")

            glanceIds.forEach { glanceId ->
                updateAppWidgetState(context, glanceId) { prefs ->
                    prefs[QuickStatsWidgetKeys.totalTilCount] = totalCount
                    prefs[QuickStatsWidgetKeys.monthlyTilCount] = monthlyCount
                    prefs[QuickStatsWidgetKeys.avgDifficulty] = avgDifficulty
                    prefs[QuickStatsWidgetKeys.achievementRate] = achievementRate
                }
                QuickStatsWidget().update(context, glanceId)
                Timber.d("QuickStatsWidgetWorker: Updated widget $glanceId - total=$totalCount, monthly=$monthlyCount, avgDiff=$avgDifficulty, rate=$achievementRate%")
            }

            Result.success()
        } catch (e: Exception) {
            Timber.e(e, "QuickStatsWidgetWorker: Failed to update widget")
            Result.failure()
        }
    }
}
