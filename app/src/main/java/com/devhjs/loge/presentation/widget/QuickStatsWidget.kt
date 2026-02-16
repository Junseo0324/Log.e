package com.devhjs.loge.presentation.widget

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.glance.GlanceId
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.currentState

class QuickStatsWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            GlanceTheme {
                val prefs = currentState<Preferences>()

                // DataStore에서 위젯 데이터 읽기
                val totalTilCount = prefs[QuickStatsWidgetKeys.totalTilCount] ?: 0
                val monthlyTilCount = prefs[QuickStatsWidgetKeys.monthlyTilCount] ?: 0
                val avgDifficulty = prefs[QuickStatsWidgetKeys.avgDifficulty] ?: 0f
                val achievementRate = prefs[QuickStatsWidgetKeys.achievementRate] ?: 0

                QuickStatsWidgetContent(
                    totalTilCount = totalTilCount,
                    monthlyTilCount = monthlyTilCount,
                    avgDifficulty = avgDifficulty,
                    achievementRate = achievementRate
                )
            }
        }
    }
}
