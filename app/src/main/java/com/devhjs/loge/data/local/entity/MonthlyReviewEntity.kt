package com.devhjs.loge.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.devhjs.loge.domain.model.AiReport
import java.util.UUID

@Entity(tableName = "monthly_reviews")
data class MonthlyReviewEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val userId: String,
    val yearMonth: String,
    val emotion: String,
    val emotionScore: Int,
    val difficultyLevel: String,
    val comment: String,
    val createdAt: Long = System.currentTimeMillis()
) {
    fun toDomain(): AiReport {
        return AiReport(
            date = createdAt,
            emotion = emotion,
            emotionScore = emotionScore,
            difficultyLevel = difficultyLevel,
            comment = comment
        )
    }
}
