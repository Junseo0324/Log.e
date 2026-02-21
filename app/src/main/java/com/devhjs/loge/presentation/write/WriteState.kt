package com.devhjs.loge.presentation.write

import com.devhjs.loge.domain.model.EmotionType

/**
 * WriteScreen의 UI 상태를 관리하는 데이터 클래스
 * MVI 패턴
 */
data class WriteState(
    val title: String = "",
    val learnings: String = "",
    val difficulties: String = "",
    val isLoading: Boolean = false,
    val isAiAnalyzing: Boolean = false,
    val showAiAnalysisResult: Boolean = false,
    val isEditMode: Boolean = false,
    val originalLogId: Long = 0,
    val createdAt: Long = System.currentTimeMillis(),
    val emotionScore: Int = 50,
    val emotion: EmotionType = EmotionType.NORMAL,
    val difficultyLevel: Int = 0,
    val aiFeedbackComment: String? = null,
    val tomorrowPlan: String = "",
    val errorMessage: String? = null
)
