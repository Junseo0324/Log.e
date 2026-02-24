package com.devhjs.loge.data.mapper

import com.devhjs.loge.data.local.entity.MonthlyReviewEntity
import com.devhjs.loge.data.remote.dto.MonthlyReviewDto

fun MonthlyReviewEntity.toRemoteDto(): MonthlyReviewDto {
    return MonthlyReviewDto(
        id = id,
        userId = userId,
        yearMonth = yearMonth,
        emotion = emotion,
        emotionScore = emotionScore,
        difficultyLevel = difficultyLevel,
        comment = comment,
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
        createdAt = System.currentTimeMillis()
    )
}
