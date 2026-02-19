package com.devhjs.loge.domain.model

data class MonthlyDashboardData(
    val stat: Stat,
    val emotionDistribution: Map<EmotionType, Int>,
    val difficultyChartPoints: List<ChartPoint>,
    val aiReport: AiReport?
)
