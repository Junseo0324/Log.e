package com.devhjs.loge.presentation.setting

import com.devhjs.loge.BuildConfig
import com.devhjs.loge.domain.model.User

/**
 * SettingScreen의 UI 상태를 관리하는 데이터 클래스
 * MVI 패턴
 */
data class SettingState(
    val user: User = User.DEFAULT,
    val isLoading: Boolean = false,
    val appVersion: String = BuildConfig.VERSION_NAME,
    val updateDate: String = BuildConfig.UPDATE_DATE,
    val showDeleteDialog: Boolean = false,
    val isExporting: Boolean = false,
    val isTimePickerVisible: Boolean = false
)
