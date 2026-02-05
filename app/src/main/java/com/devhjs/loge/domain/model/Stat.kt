package com.devhjs.loge.domain.model

data class Stat(
    val date: String, // YYYY-MM
    val totalTil: Int,
    val avgEmotion: Float,
    val avgScore: Float,
    val avgDifficulty: Float,
    val emotionScoreList: List<ChartPoint>,
    val learnedDates: List<String>, // YYYY-MM-DD
    val aiReport: AiReport? = null
)

