package com.devhjs.loge.data.util

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.devhjs.loge.data.worker.ActivityWidgetWorker
import com.devhjs.loge.data.worker.QuickStatsWidgetWorker
import com.devhjs.loge.data.worker.WeeklyTilWidgetWorker
import com.devhjs.loge.domain.util.WidgetUpdateManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RealWidgetUpdateManager @Inject constructor(
    @ApplicationContext private val context: Context
) : WidgetUpdateManager {

    override fun updateAllWidgets() {
        val workManager = WorkManager.getInstance(context)

        workManager.enqueueUniqueWork(
            "WeeklyTilWidgetUpdate",
            ExistingWorkPolicy.REPLACE,
            OneTimeWorkRequestBuilder<WeeklyTilWidgetWorker>().build()
        )

        workManager.enqueueUniqueWork(
            "ActivityWidgetUpdate",
            ExistingWorkPolicy.REPLACE,
            OneTimeWorkRequestBuilder<ActivityWidgetWorker>().build()
        )

        workManager.enqueueUniqueWork(
            "QuickStatsWidgetUpdate",
            ExistingWorkPolicy.REPLACE,
            OneTimeWorkRequestBuilder<QuickStatsWidgetWorker>().build()
        )
    }
}
