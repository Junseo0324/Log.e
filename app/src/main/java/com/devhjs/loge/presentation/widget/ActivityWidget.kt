package com.devhjs.loge.presentation.widget

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.glance.GlanceId
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.currentState

class ActivityWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            GlanceTheme {
                val prefs = currentState<Preferences>()
                
                // 저장된 활동 일수 읽어오기
                val activeDayCount = prefs[ActivityWidgetKeys.activeDayCount] ?: 0

                ActivityWidgetContent(activeDayCount = activeDayCount)
            }
        }
    }
}
