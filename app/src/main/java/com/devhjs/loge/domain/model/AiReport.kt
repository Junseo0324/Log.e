package com.devhjs.loge.domain.model

data class AiReport(
    val date: Long,
    val emotion: String,
    val score: Int,
    val difficulty: String,
    val comment: String
)
