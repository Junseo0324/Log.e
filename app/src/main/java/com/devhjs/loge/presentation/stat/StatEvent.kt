package com.devhjs.loge.presentation.stat

/**
 * StatScreen에서 발생하는 일회성 이벤트
 * MVI 패턴
 */
sealed interface StatEvent {
    data class ShowError(val message: String) : StatEvent
    data object ShowMonthlyRewardAd : StatEvent // 월간 재분석을 위한 리워드 광고 요청
}
