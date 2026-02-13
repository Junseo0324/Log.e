package com.devhjs.loge.domain.model

/**
 * 주간 TIL 통계 정보
 * @property totalCount 이번 주 작성한 총 TIL 개수
 * @property dailyActivity 요일별 작성 여부 (Index 0: 일요일 ~ Index 6: 토요일)
 */
data class WeeklyStats(
    val totalCount: Int,
    val dailyActivity: List<Boolean>
)
