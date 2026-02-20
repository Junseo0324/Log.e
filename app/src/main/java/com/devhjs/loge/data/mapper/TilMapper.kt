package com.devhjs.loge.data.mapper

import com.devhjs.loge.data.dto.TilRemoteDto
import com.devhjs.loge.data.local.entity.TilEntity
import com.devhjs.loge.domain.model.EmotionType
import com.devhjs.loge.domain.model.Til

fun TilEntity.toDomain(): Til {
    return Til(
        id = id,
        createdAt = createdAt,
        title = title,
        learned = learned,
        difficult = difficult,
        emotionScore = emotionScore,
        emotion = EmotionType.fromString(emotion),
        difficultyLevel = difficultyLevel,
        updatedAt = updatedAt,
        aiFeedBack = aiFeedBack,
        tomorrowPlan = tomorrowPlan
    )
}

fun Til.toEntity(): TilEntity {
    return TilEntity(
        id = id,
        createdAt = createdAt,
        title = title,
        learned = learned,
        difficult = difficult,
        emotionScore = emotionScore,
        emotion = emotion.label,
        difficultyLevel = difficultyLevel,
        updatedAt = updatedAt,
        aiFeedBack = aiFeedBack,
        tomorrowPlan = tomorrowPlan
    )
}

/**
 * 도메인 모델 → Supabase DTO 변환
 * userId: Supabase Auth UID
 */
fun Til.toRemoteDto(userId: String): TilRemoteDto {
    return TilRemoteDto(
        userId = userId,
        localId = id,
        createdAt = createdAt,
        title = title,
        learned = learned,
        difficult = difficult,
        emotionScore = emotionScore,
        emotion = emotion.label,
        difficultyLevel = difficultyLevel,
        updatedAt = updatedAt,
        aiFeedback = aiFeedBack,
        tomorrowPlan = tomorrowPlan
    )
}
