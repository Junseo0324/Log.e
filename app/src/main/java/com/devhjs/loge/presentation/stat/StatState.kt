package com.devhjs.loge.presentation.stat

import androidx.compose.runtime.Stable
import com.devhjs.loge.domain.model.AiReport
import com.devhjs.loge.domain.model.ChartPoint
import com.devhjs.loge.domain.model.EmotionType
import com.devhjs.loge.domain.model.Stat
import java.time.YearMonth
import java.time.format.DateTimeFormatter

/**
 * StatScreen의 UI 상태를 관리하는 데이터 클래스
 */
@Stable
data class StatState(
    val isLoading: Boolean = true,
    val selectedMonth: String = YearMonth.now().format(DateTimeFormatter.ofPattern("yyyy-MM")),
    val stat: Stat? = null,
    val emotionDistribution: Map<EmotionType, Int> = emptyMap(),
    val difficultyChartPoints: List<ChartPoint> = emptyList(),
    val yearlyLearnedDates: List<String> = emptyList(), // 연간 학습 날짜 (ContributionGraph용)
    val aiReport: AiReport? = null,
    val isAiLoading: Boolean = false,
    val totalLogCount: Int = 0,             // 해당 월 TIL 총 개수 (최소 10개 필요)
    val isMonthlyAnalyzed: Boolean = false, // 이번 달 이미 분석 여부
    val canMonthlyAnalyze: Boolean = false, // 10개 이상 && 미분석 시 true
    val monthlyLimitMsg: String? = null     // 버튼 하단 안내 문구
)
