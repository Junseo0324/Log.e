package com.devhjs.loge.domain.model
/**
 * 피드백 유형
 */
enum class FeedbackType(val value: String) {
    BUG("bug"),
    FEATURE("feature"),
    OTHER("other")
}