package com.devhjs.loge.presentation.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.devhjs.loge.data.worker.WeeklyTilWidgetWorker

class WeeklyTilWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = WeeklyTilWidget()

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        enqueueWorker(context)
    }

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        enqueueWorker(context)
    }

    private fun enqueueWorker(context: Context) {
        val workRequest = OneTimeWorkRequestBuilder<WeeklyTilWidgetWorker>().build()
        WorkManager.getInstance(context).enqueueUniqueWork(
            "WeeklyTilWidgetUpdate",
            ExistingWorkPolicy.KEEP,
            workRequest
        )
    }
}
