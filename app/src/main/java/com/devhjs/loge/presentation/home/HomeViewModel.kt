package com.devhjs.loge.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhjs.loge.domain.model.Til
import com.devhjs.loge.domain.usecase.GetTilsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

/**
 * HomeScreen의 비즈니스 로직을 담당하는 ViewModel
 * MVI 패턴에서 State와 Event를 관리
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTilsUseCase: GetTilsUseCase
) : ViewModel() {

    // UI 상태 (StateFlow로 관리)
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    // 일회성 이벤트 (Channel로 관리)
    private val _event = MutableSharedFlow<HomeEvent>()
    val event = _event.asSharedFlow()

    init {
        loadLogs()
    }

    /**
     * 로그 데이터 로드
     * 현재 월의 시작~끝 범위로 조회
     */
    fun loadLogs() {
        val (startOfMonth, endOfMonth) = getMonthRange()

        viewModelScope.launch {
            getTilsUseCase(startOfMonth, endOfMonth)
                .onStart {
                    _state.update { it.copy(isLoading = true, errorMessage = null) }
                }
                .catch { exception ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = exception.message
                        )
                    }
                    _event.send(HomeEvent.ShowError(exception.message ?: "알 수 없는 오류가 발생했습니다."))
                }
                .collect { tils ->
                    // Til -> LogItem 변환
                    val logItems = tils.map { til ->
                        til.toLogItem()
                    }.sortedByDescending { it.id } // 최신순 정렬

                    val currentDate = getCurrentDateFormatted()
                    
                    _state.update {
                        it.copy(
                            isLoading = false,
                            logs = logItems,
                            currentDate = currentDate,
                            totalLogCount = logItems.size
                        )
                    }
                }
        }
    }

    /**
     * 추가 버튼 클릭 시 호출
     */
    fun onAddClick() {
        viewModelScope.launch {
            _event.send(HomeEvent.NavigateToWrite)
        }
    }

    /**
     * 로그 카드 클릭 시 호출
     */
    fun onLogClick(logId: Long) {
        viewModelScope.launch {
            _event.send(HomeEvent.NavigateToDetail(logId))
        }
    }

    // ========== Private Helper Functions ==========

    /**
     * Til 도메인 모델을 LogItem UI 모델로 변환
     */
    private fun Til.toLogItem(): LogItem {
        val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
        val timeFormat = SimpleDateFormat("a hh:mm", Locale.KOREA)
        val dayOfWeekFormat = SimpleDateFormat("E", Locale.KOREA)
        
        val date = Date(this.createdAt)
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val logDate = Calendar.getInstance().apply {
            timeInMillis = this@toLogItem.createdAt
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        return LogItem(
            id = this.id,
            emotion = EmotionType.fromString(this.emotion),
            emotionScore = this.emotionScore,
            time = timeFormat.format(date),
            title = this.title,
            content = this.learned,
            level = this.difficultyLevel,
            date = dateFormat.format(date),
            dayOfWeek = dayOfWeekFormat.format(date),
            isToday = logDate == today
        )
    }

    /**
     * 현재 월의 시작과 끝 timestamp 반환
     */
    private fun getMonthRange(): Pair<Long, Long> {
        val calendar = Calendar.getInstance()
        
        // 월의 시작
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfMonth = calendar.timeInMillis

        // 월의 끝
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        val endOfMonth = calendar.timeInMillis

        return startOfMonth to endOfMonth
    }

    /**
     * 현재 날짜를 "yyyy.MM.dd" 형식으로 반환
     */
    private fun getCurrentDateFormatted(): String {
        val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
        return dateFormat.format(Date())
    }
}
