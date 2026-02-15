package com.devhjs.loge.presentation.feedback

sealed interface FeedbackEvent {
    data object SubmitSuccess : FeedbackEvent
    data class ShowSnackbar(val message: String) : FeedbackEvent
}
