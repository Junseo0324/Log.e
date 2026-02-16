package com.devhjs.loge.data.worker

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.devhjs.loge.domain.usecase.GetRecentActivityDaysUseCase
import com.devhjs.loge.presentation.widget.ActivityWidget
import com.devhjs.loge.presentation.widget.ActivityWidgetKeys
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

import com.devhjs.loge.core.util.Result as AppResult

@HiltWorker
class ActivityWidgetWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val getRecentActivityDaysUseCase: GetRecentActivityDaysUseCase
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return when (val result = getRecentActivityDaysUseCase()) {
            is AppResult.Success -> {
                val activeDayCount = result.data
                updateWidget(activeDayCount)
                Result.success()
            }
            is AppResult.Error -> {
                Timber.e(result.error, "ActivityWidgetWorker: Failed to get activity days")
                Result.failure()
            }
        }
    }

    private suspend fun updateWidget(activeDayCount: Int) {
        try {
            // Glance 위젯 상태 업데이트
            val manager = GlanceAppWidgetManager(context)
            val glanceIds = manager.getGlanceIds(ActivityWidget::class.java)

            Timber.d("ActivityWidgetWorker: Found ${glanceIds.size} widgets. Active Days: $activeDayCount")

            glanceIds.forEach { glanceId ->
                updateAppWidgetState(context, glanceId) { prefs ->
                    prefs[ActivityWidgetKeys.activeDayCount] = activeDayCount
                }
                ActivityWidget().update(context, glanceId)
            }
        } catch (e: Exception) {
            Timber.e(e, "ActivityWidgetWorker: Failed to update widget state")
        }
    }
}
