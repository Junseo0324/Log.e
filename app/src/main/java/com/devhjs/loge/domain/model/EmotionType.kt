package com.devhjs.loge.domain.model

enum class EmotionType(val label: String) {
    FULFILLMENT("성취감"), // 90~100
    SATISFACTION("만족"),  // 70~89
    NORMAL("평범"),        // 50~69
    DIFFICULTY("어려움"),  // 30~49
    FRUSTRATION("좌절"); // 0~29

    companion object {
        fun fromScore(score: Int): EmotionType {
            return when {
                score >= 90 -> FULFILLMENT
                score >= 70 -> SATISFACTION
                score >= 50 -> NORMAL
                score >= 30 -> DIFFICULTY
                else -> FRUSTRATION
            }
        }

        fun fromString(emotion: String): EmotionType {
            return when (emotion) {
                "성취감" -> FULFILLMENT
                "만족" -> SATISFACTION
                "평범" -> NORMAL
                "어려움" -> DIFFICULTY
                "좌절" -> FRUSTRATION
                
                else -> NORMAL
            }
        }
    }
}
