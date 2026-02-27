package com.devhjs.loge.presentation.setting

/**
 * SettingScreen에서 발생하는 사용자 액션
 * MVI 패턴
 */
sealed interface SettingAction {
    data class OnNotificationToggle(val enabled: Boolean) : SettingAction
    data class OnDarkModeToggle(val enabled: Boolean) : SettingAction
    data object OnProfileClick : SettingAction
    data object OnExportClick : SettingAction
    data object OnDeleteAllClick : SettingAction
    data object OnDeleteConfirm : SettingAction
    data object OnDeleteDismiss : SettingAction
    data object OnLicensesClick : SettingAction
    data class OnExportUriSelected(val uri: String) : SettingAction
    data class OnTimeSelected(val hour: Int, val minute: Int) : SettingAction
    data object OnTimePickerClick : SettingAction
    data object OnTimePickerDismiss : SettingAction
    data object OnFeedbackClick : SettingAction
}
