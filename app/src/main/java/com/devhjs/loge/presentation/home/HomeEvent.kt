package com.devhjs.loge.presentation.home

/**
 * HomeScreen에서 발생하는 일회성 이벤트를 정의하는 sealed interface
 * Navigation, Snackbar 등 한 번만 처리되어야 하는 이벤트 (MVI 패턴)
 */
sealed interface HomeEvent {
    // 로그 상세 화면으로 이동
    data class NavigateToDetail(val logId: Long) : HomeEvent
    
    // 로그 작성 화면으로 이동
    data object NavigateToWrite : HomeEvent
    
    // 에러 메시지 표시
    data class ShowError(val message: String) : HomeEvent
}
