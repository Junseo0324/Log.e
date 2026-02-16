package com.devhjs.loge.presentation.widget

import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey

// Quick Stats 위젯에서 사용하는 DataStore 키 정의
object QuickStatsWidgetKeys {
    val totalTilCount = intPreferencesKey("quickStats_totalTilCount")       // 전체 TIL 수
    val monthlyTilCount = intPreferencesKey("quickStats_monthlyTilCount")   // 이번달 TIL 수
    val avgDifficulty = floatPreferencesKey("quickStats_avgDifficulty")     // 평균 난이도
    val achievementRate = intPreferencesKey("quickStats_achievementRate")   // 달성률 (%)
}
