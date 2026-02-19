package com.devhjs.loge.presentation.stat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.usecase.GetEmotionDistributionUseCase
import com.devhjs.loge.domain.usecase.GetMonthlyReviewUseCase
import com.devhjs.loge.domain.usecase.GetMonthlyStatUseCase
import com.devhjs.loge.domain.usecase.GetYearlyLearnedDatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class StatViewModel @Inject constructor(
    private val getMonthlyStatUseCase: GetMonthlyStatUseCase,
    private val getEmotionDistributionUseCase: GetEmotionDistributionUseCase,
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

            combine(
                getMonthlyStatUseCase(monthString),
                getEmotionDistributionUseCase(monthString),
                flow {
                    emit(getMonthlyReviewUseCase(monthString, forceFetchFromAi = false))
                }
            ) { stat, tilAnalysis, reviewResult ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        stat = stat,
                        emotionDistribution = tilAnalysis.emotionDistribution,
                        difficultyChartPoints = tilAnalysis.difficultyChartPoints,
                        aiReport = (reviewResult as? Result.Success)?.data
                    )
                }
            }.catch { e ->
                _state.update { it.copy(isLoading = false) }
                _event.emit(StatEvent.ShowError(e.message ?: "통계 데이터를 불러오는데 실패했습니다."))
            }.collect {}
        }

        // 연간 학습 날짜 로드 (연도가 바뀔 때만 재로딩)
        val year = _state.value.selectedMonth.substring(0, 4).toInt()
        if (year != loadedYear) {
            loadedYear = year
            yearlyJob?.cancel()
            yearlyJob = viewModelScope.launch {
                getYearlyLearnedDatesUseCase(year)
                    .catch { /* 연간 데이터 실패 시 무시 */ }
                    .collect { dates ->
                        _state.update { it.copy(yearlyLearnedDates = dates) }
                    }
            }
        }
    }
}
