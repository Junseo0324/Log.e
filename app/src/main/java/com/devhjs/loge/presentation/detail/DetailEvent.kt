package com.devhjs.loge.presentation.detail

sealed interface DetailEvent {
    data object NavigateBack : DetailEvent
    data class NavigateToEdit(val logId: Long) : DetailEvent
    data class ShowError(val message: String) : DetailEvent
}