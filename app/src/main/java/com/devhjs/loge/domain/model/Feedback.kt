package com.devhjs.loge.domain.model

/**
 * 피드백 도메인 모델
 */
data class Feedback(
    val type: FeedbackType,
    val title: String,
    val content: String
)
