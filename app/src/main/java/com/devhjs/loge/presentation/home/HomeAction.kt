package com.devhjs.loge.presentation.home

/**
 * HomeScreen에서 발생하는 사용자 액션을 모아놓은 데이터 클래스
 * 콜백 함수들을 하나의 객체로 모아서 관리 (MVI 패턴)
 */
sealed interface HomeAction {
    data object OnAddClick : HomeAction
    data class OnLogClick(val id: Long) : HomeAction
    data object OnRefresh : HomeAction
}
