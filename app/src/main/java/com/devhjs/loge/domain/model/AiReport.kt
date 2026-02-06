package com.devhjs.loge.domain.model

data class AiReport(
    val date: Long,
    val emotion: String,
    val emotionScore: Int,
    val difficultyLevel: String,
    val comment: String
)
