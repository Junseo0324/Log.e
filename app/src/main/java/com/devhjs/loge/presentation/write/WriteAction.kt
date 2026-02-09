package com.devhjs.loge.presentation.write

/**
 * WriteScreen에서 발생하는 사용자 액션
 * MVI 패턴
 */
sealed interface WriteAction {
    data class OnTitleChange(val title: String) : WriteAction
    data class OnLearningsChange(val learnings: String) : WriteAction
    data class OnDifficultiesChange(val difficulties: String) : WriteAction
    data object OnSaveClick : WriteAction
    data object OnBackClick : WriteAction
    data object OnAiAnalyzeClick : WriteAction
    data object OnConsumeError : WriteAction
}
