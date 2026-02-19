package com.devhjs.loge.data.remote.dto

import com.devhjs.loge.data.local.entity.MonthlyReviewEntity
import com.devhjs.loge.domain.model.AiReport
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

fun MonthlyReviewEntity.toRemoteDto(): MonthlyReviewDto {
    return MonthlyReviewDto(
        id = id,
        userId = userId,
        yearMonth = yearMonth,
        emotion = emotion,
        emotionScore = emotionScore,
        difficultyLevel = difficultyLevel,
        comment = comment,
        // created_at은 Supabase에서 자동 생성되거나 필요 시 변환
    )
}

fun MonthlyReviewDto.toEntity(): MonthlyReviewEntity {
    return MonthlyReviewEntity(
        id = id ?: "",
        userId = userId,
        yearMonth = yearMonth,
        emotion = emotion,
        emotionScore = emotionScore,
        difficultyLevel = difficultyLevel,
        comment = comment,
        createdAt = System.currentTimeMillis() // 원격 시간을 파싱하려면 DateTimeFormatter 필요
    )
}
