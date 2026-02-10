package com.devhjs.loge.domain.model

/**
 * 연간 활동 그래프 그리드 정보
 * @param startOffset 1월 1일의 요일 오프셋 (월요일=0, 일요일=6)
 * @param totalDays 해당 연도의 총 일 수 (365 또는 366)
 * @param totalWeeks 그리드에 필요한 총 주(열) 수
 * @param monthLabels 각 월 라벨과 해당 주 인덱스
 */
data class YearGridInfo(
    val startOffset: Int,
    val totalDays: Int,
    val totalWeeks: Int,
    val monthLabels: List<Pair<Int, String>>
)
