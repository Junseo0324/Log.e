package com.devhjs.loge.data.worker

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.devhjs.loge.domain.usecase.GetQuickStatsUseCase
import com.devhjs.loge.presentation.widget.QuickStatsWidget
import com.devhjs.loge.presentation.widget.QuickStatsWidgetKeys
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

@HiltWorker
class QuickStatsWidgetWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val getQuickStatsUseCase: GetQuickStatsUseCase
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val stats = getQuickStatsUseCase()

            val manager = GlanceAppWidgetManager(context)
            val glanceIds = manager.getGlanceIds(QuickStatsWidget::class.java)

            Timber.d("QuickStatsWidgetWorker: Found ${glanceIds.size} widgets")

            glanceIds.forEach { glanceId ->
                updateAppWidgetState(context, glanceId) { prefs ->
                    prefs[QuickStatsWidgetKeys.totalTilCount] = stats.totalCount
                    prefs[QuickStatsWidgetKeys.monthlyTilCount] = stats.monthlyCount
                    prefs[QuickStatsWidgetKeys.avgDifficulty] = stats.avgDifficulty
                    prefs[QuickStatsWidgetKeys.achievementRate] = stats.achievementRate
                }
                QuickStatsWidget().update(context, glanceId)
                Timber.d("QuickStatsWidgetWorker: Updated widget $glanceId - total=${stats.totalCount}, monthly=${stats.monthlyCount}, avgDiff=${stats.avgDifficulty}, rate=${stats.achievementRate}%")
            }

            Result.success()
        } catch (e: Exception) {
            Timber.e(e, "QuickStatsWidgetWorker: Failed to update widget")
            Result.failure()
        }
    }
}
