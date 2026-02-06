package com.devhjs.loge.presentation.home

import androidx.compose.ui.graphics.Color
import com.devhjs.loge.presentation.designsystem.AppColors

/**
 * HomeScreen의 UI 상태를 관리하는 데이터 클래스
 * 모든 UI 상태를 하나의 클래스로 모아서 관리 (MVI 패턴)
 */
data class HomeState(
    val isLoading: Boolean = false,
    val logs: List<LogItem> = emptyList(),
    val currentDate: String = "",
    val totalLogCount: Int = 0,
    val errorMessage: String? = null
)

/**
 * UI 표시용 로그 아이템
 * Domain 모델(Til)을 UI에 맞게 변환한 모델
 */
data class LogItem(
    val id: Long,
    val emotion: EmotionType,
    val emotionScore: Int,
    val time: String,
    val title: String,
    val content: String,
    val level: Int,
    val date: String,
    val dayOfWeek: String,
    val isToday: Boolean = false
)

/**
 * 감정 타입 enum
 * 감정별 라벨과 색상을 정의
 */
enum class EmotionType(val label: String, val color: Color) {
    HAPPY("기쁨", AppColors.primary),
    CONFUSED("혼란", Color(0xFFFFA500)),
    STRUGGLE("고군분투", AppColors.red);

    companion object {
        // 감정 점수에 따라 EmotionType 결정
        fun fromScore(score: Int): EmotionType {
            return when {
                score >= 70 -> HAPPY
                score >= 40 -> CONFUSED
                else -> STRUGGLE
            }
        }

        // 감정 문자열에서 EmotionType 변환
        fun fromString(emotion: String): EmotionType {
            return when (emotion) {
                "기쁨" -> HAPPY
                "혼란" -> CONFUSED
                "고군분투" -> STRUGGLE
                else -> CONFUSED
            }
        }
    }
}
