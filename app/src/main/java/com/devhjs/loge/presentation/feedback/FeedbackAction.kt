package com.devhjs.loge.presentation.feedback

import com.devhjs.loge.domain.model.FeedbackType

sealed interface FeedbackAction {
    data class OnTypeSelected(val type: FeedbackType) : FeedbackAction
    data class OnTitleChanged(val title: String) : FeedbackAction
    data class OnContentChanged(val content: String) : FeedbackAction
    data object OnSubmit : FeedbackAction
    data object OnCancel : FeedbackAction
}
