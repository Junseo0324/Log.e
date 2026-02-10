package com.devhjs.loge.domain.model

data class TilAnalysis(
    val emotionDistribution: Map<EmotionType, Int>,
    val difficultyChartPoints: List<ChartPoint>
)