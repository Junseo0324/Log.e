package com.devhjs.loge.presentation.write

/**
 * WriteScreen에서 발생하는 사용자 액션
 * MVI 패턴
 */
sealed interface WriteAction {
    data class OnTitleChange(val title: String) : WriteAction
    data class OnLearningsChange(val learnings: String) : WriteAction
    data class OnDifficultiesChange(val difficulties: String) : WriteAction
    data class OnTomorrowPlanChange(val tomorrowPlan: String) : WriteAction
    data object OnSaveClick : WriteAction
    data object OnBackClick : WriteAction
    data object OnAiAnalyzeClick : WriteAction      // AI 분석 버튼 클릭 (일일 제한 체크)
    data object OnAiAnalyzeAfterAd : WriteAction    // 광고 시청 완료 후 AI 분석
    data object OnConsumeError : WriteAction
}
