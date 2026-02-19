package com.devhjs.loge.presentation.stat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.usecase.GetMonthlyDashboardUseCase
import com.devhjs.loge.domain.usecase.GetMonthlyReviewUseCase
import com.devhjs.loge.domain.usecase.GetYearlyLearnedDatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class StatViewModel @Inject constructor(
    private val getMonthlyDashboardUseCase: GetMonthlyDashboardUseCase,
    private val getYearlyLearnedDatesUseCase: GetYearlyLearnedDatesUseCase,
    private val getMonthlyReviewUseCase: GetMonthlyReviewUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(StatState())
    val state: StateFlow<StatState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<StatEvent>()
    val event = _event.asSharedFlow()

    // 데이터 로딩 Job을 관리하여 월 변경 시 이전 Job 취소
    private var loadJob: Job? = null
    private var yearlyJob: Job? = null

    // 현재 로드된 연도를 추적 (같은 연도면 재로딩 방지)
    private var loadedYear: Int? = null

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM")

    init {
        loadStats()
    }

    fun onAction(action: StatAction) {
        when (action) {
            is StatAction.OnPreviousMonthClick -> {
                val prevMonth = YearMonth.parse(_state.value.selectedMonth)
                    .minusMonths(1).format(formatter)
                _state.update { it.copy(selectedMonth = prevMonth) }
                loadStats()
            }
            is StatAction.OnNextMonthClick -> {
                val nextMonth = YearMonth.parse(_state.value.selectedMonth)
                    .plusMonths(1).format(formatter)
                _state.update { it.copy(selectedMonth = nextMonth) }
                loadStats()
            }
            is StatAction.OnAiAnalyzeClick -> {
                analyzeMonthlyLog()
            }
        }
    }

    private fun analyzeMonthlyLog() {
        viewModelScope.launch {
            _state.update { it.copy(isAiLoading = true) }
            val result = getMonthlyReviewUseCase(_state.value.selectedMonth)
            
            _state.update { it.copy(isAiLoading = false) }
            
            when (result) {
                is Result.Success -> {
                    _state.update { it.copy(aiReport = result.data) }
                }
                is Result.Error -> {
                    _event.emit(StatEvent.ShowError(result.error.message ?: "AI 분석에 실패했습니다."))
                }
            }
        }
    }

    /**
     * 선택된 월의 통계 데이터와 감정 분포를 동시에 로드
     */
    private fun loadStats() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            _state.update { 
                it.copy(
                    isLoading = true,
                    aiReport = null
                ) 
            }

            val monthString = _state.value.selectedMonth

            getMonthlyDashboardUseCase(monthString)
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            val dashboardData = result.data
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    stat = dashboardData.stat,
                                    emotionDistribution = dashboardData.emotionDistribution,
                                    difficultyChartPoints = dashboardData.difficultyChartPoints,
                                    aiReport = dashboardData.aiReport
                                )
                            }
                        }
                        is Result.Error -> {
                            _state.update { it.copy(isLoading = false) }
                            _event.emit(StatEvent.ShowError(result.error.message ?: "통계 데이터를 불러오는데 실패했습니다."))
                        }
                    }
                }
        }

        // 연간 학습 날짜 로드 (연도가 바뀔 때만 재로딩)
        val year = _state.value.selectedMonth.substring(0, 4).toInt()
        if (year != loadedYear) {
            loadedYear = year
            yearlyJob?.cancel()
            yearlyJob = viewModelScope.launch {
                getYearlyLearnedDatesUseCase(year)
                    .collect { result ->
                        if (result is Result.Success) {
                            _state.update { it.copy(yearlyLearnedDates = result.data) }
                        }
                    }
            }
        }
    }
}
