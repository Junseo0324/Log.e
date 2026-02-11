package com.devhjs.loge.presentation.stat

/**
 * StatScreen에서 발생하는 사용자 액션
 * MVI 패턴
 */
sealed interface StatAction {
    data object OnPreviousMonthClick : StatAction
    data object OnNextMonthClick : StatAction
    data object OnAiAnalyzeClick : StatAction
}
