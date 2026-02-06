package com.devhjs.loge.domain.model

data class Til(
    val id: Long = 0,
    val createdAt: Long,
    val title: String,
    val learned: String,
    val difficult: String,
    val emotionScore: Int,
    val emotion: EmotionType,
    val difficultyLevel: Int,
    val updatedAt: Long,
    val aiFeedBack: String? = null
)