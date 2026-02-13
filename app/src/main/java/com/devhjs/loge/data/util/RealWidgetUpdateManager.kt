package com.devhjs.loge.data.util

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.devhjs.loge.data.worker.WeeklyTilWidgetWorker
import com.devhjs.loge.domain.util.WidgetUpdateManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RealWidgetUpdateManager @Inject constructor(
    @ApplicationContext private val context: Context
) : WidgetUpdateManager {

    override fun updateWeeklyWidget() {
        val workRequest = OneTimeWorkRequestBuilder<WeeklyTilWidgetWorker>().build()
        WorkManager.getInstance(context).enqueueUniqueWork(
            "WeeklyTilWidgetUpdate",
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
    }
}
