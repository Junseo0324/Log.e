package com.devhjs.loge.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TilRemoteDto(
    @SerialName("user_id")
    val userId: String,
    @SerialName("local_id")
    val localId: Long,
    @SerialName("created_at")
    val createdAt: Long,
    @SerialName("title")
    val title: String,
    @SerialName("learned")
    val learned: String,
    @SerialName("difficult")
    val difficult: String,
    @SerialName("emotion_score")
    val emotionScore: Int,
    @SerialName("emotion")
    val emotion: String,
    @SerialName("difficulty_level")
    val difficultyLevel: Int,
    @SerialName("updated_at")
    val updatedAt: Long,
    @SerialName("ai_feedback")
    val aiFeedback: String? = null
)
