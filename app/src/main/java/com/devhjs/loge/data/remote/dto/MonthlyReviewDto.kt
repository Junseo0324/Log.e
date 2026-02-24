package com.devhjs.loge.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MonthlyReviewDto(
    val id: String? = null,
    @SerialName("user_id")
    val userId: String,
    @SerialName("year_month")
    val yearMonth: String,
    val emotion: String,
    @SerialName("emotion_score")
    val emotionScore: Int,
    @SerialName("difficulty_level")
    val difficultyLevel: String,
    val comment: String,
    @SerialName("created_at")
    val createdAt: String? = null
)


