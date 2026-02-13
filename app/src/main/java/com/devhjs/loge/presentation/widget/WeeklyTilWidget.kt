package com.devhjs.loge.presentation.widget

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.glance.GlanceId
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.currentState
import com.devhjs.loge.domain.model.WeeklyStats

class WeeklyTilWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            GlanceTheme {
                val prefs = currentState<Preferences>()
                val totalCount = prefs[WeeklyTilWidgetKeys.totalCount] ?: 0
                val dailyActivityString = prefs[WeeklyTilWidgetKeys.dailyActivity] ?: ""

                val dailyActivity = if (dailyActivityString.isNotEmpty()) {
                    dailyActivityString.split(",").map { it.toBoolean() }
                } else {
                    List(7) { false }
                }

                val weeklyStats = WeeklyStats(totalCount, dailyActivity)

                WeeklyTilWidgetContent(weeklyStats = weeklyStats)
            }
        }
    }
}
