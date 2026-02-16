package com.devhjs.loge.presentation.widget

import androidx.datastore.preferences.core.intPreferencesKey

object ActivityWidgetKeys {
    // 최근 14일간 활동한 총 일수
    val activeDayCount = intPreferencesKey("activityWidget_activeDayCount")
}
