package com.devhjs.loge.data.worker

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.devhjs.loge.domain.usecase.GetWeeklyTilCountUseCase
import com.devhjs.loge.presentation.widget.WeeklyTilWidget
import com.devhjs.loge.presentation.widget.WeeklyTilWidgetKeys
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import timber.log.Timber

@HiltWorker
class WeeklyTilWidgetWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val getWeeklyTilCountUseCase: GetWeeklyTilCountUseCase
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            // 주간 통계 데이터 가져오기
            val count = getWeeklyTilCountUseCase().first()

            // Glance 위젯 상태 업데이트
            val manager = GlanceAppWidgetManager(context)
            val glanceIds = manager.getGlanceIds(WeeklyTilWidget::class.java)

            Timber.d("WeeklyTilWidgetWorker: Found ${glanceIds.size} widgets")

            glanceIds.forEach { glanceId ->
                updateAppWidgetState(context, glanceId) { prefs ->
                    prefs[WeeklyTilWidgetKeys.totalCount] = count
                }
                WeeklyTilWidget().update(context, glanceId)
                Timber.d("WeeklyTilWidgetWorker: Updated widget $glanceId with count $count")
            }

            Result.success()
        } catch (e: Exception) {
            Timber.e(e, "WeeklyTilWidgetWorker: Failed to update widget")
            Result.failure()
        }
    }
}
