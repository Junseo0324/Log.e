package com.devhjs.loge.presentation.profile

/**
 * ProfileScreen에서 발생하는 일회성 이벤트 (MVI 패턴)
 */
sealed interface ProfileEvent {
    data object NavigateBack : ProfileEvent
    data class SubmitSuccess(val message: String) : ProfileEvent
    data class ShowSnackbar(val message: String) : ProfileEvent
}
