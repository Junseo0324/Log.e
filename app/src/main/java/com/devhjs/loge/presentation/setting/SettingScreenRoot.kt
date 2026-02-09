package com.devhjs.loge.presentation.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun SettingScreenRoot(
    modifier: Modifier = Modifier
) {
    var isNotificationEnabled by remember { mutableStateOf(true) }
    var isAutoAnalysisEnabled by remember { mutableStateOf(false) }

    SettingScreen(
        onNotificationToggle = { isNotificationEnabled = it },
        isNotificationEnabled = isNotificationEnabled,
        onAutoAnalysisToggle = { isAutoAnalysisEnabled = it },
        isAutoAnalysisEnabled = isAutoAnalysisEnabled,
        onExportClick = { 
            // TODO: 데이터 내보내기 로직 구현
        },
        onDeleteAllClick = { 
            // TODO: 전체 삭제 로직 구현 및 다이얼로그 표시
        },
        modifier = modifier
    )
}