package com.devhjs.loge.presentation.write

/**
 * WriteScreen에서 발생하는 일회성 이벤트
 * MVI 패턴
 */
sealed interface WriteEvent {
    data object NavigateBack : WriteEvent
    data class SubmitSuccess(val message: String) : WriteEvent
    data class ShowError(val message: String) : WriteEvent
}
