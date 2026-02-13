package com.devhjs.loge.presentation.widget

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object WeeklyTilWidgetKeys {
    val totalCount = intPreferencesKey("totalCount")
    val dailyActivity = stringPreferencesKey("dailyActivity")
}
