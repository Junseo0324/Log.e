package com.devhjs.loge.presentation.setting

/**
 * SettingScreen에서 발생하는 일회성 이벤트
 * MVI 패턴
 */
sealed interface SettingEvent {
    data object NavigateToProfile : SettingEvent
    data object NavigateToLicenses : SettingEvent
    data class ShowSnackbar(val message: String) : SettingEvent
    data class LaunchExport(val fileName: String) : SettingEvent
}
