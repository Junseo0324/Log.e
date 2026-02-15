package com.devhjs.loge.presentation.feedback

import com.devhjs.loge.domain.model.FeedbackType

data class FeedbackState(
    val type: FeedbackType = FeedbackType.BUG,
    val title: String = "",
    val content: String = "",
    val isLoading: Boolean = false
)
