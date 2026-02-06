package com.devhjs.loge.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhjs.loge.core.util.DateUtils
import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.usecase.GetTilsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

    // 일회성 이벤트
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
        val (startOfMonth, endOfMonth) = DateUtils.getCurrentMonthStartEnd()

        viewModelScope.launch {
            getTilsUseCase(startOfMonth, endOfMonth)
                .onStart {
                    _state.update { it.copy(isLoading = true, errorMessage = null) }
                }
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            val tils = result.data
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    logs = tils,
                                    currentDate = DateUtils.getTodayString(),
                                    totalLogCount = tils.size
                                )
                            }
                        }
                        is Result.Error -> {
                            val error = result.error
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = error.message
                                )
                            }
                            _event.emit(HomeEvent.ShowError(error.message ?: "다시 시도해주세요."))
                        }
                    }
                }
        }
    }

    /**
     * 추가 버튼 클릭 시 호출
     */
    /**
     * 사용자 액션 처리 (MVI 패턴)
     */
    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnAddClick -> {
                viewModelScope.launch {
                    _event.emit(HomeEvent.NavigateToWrite)
                }
            }
            is HomeAction.OnLogClick -> {
                viewModelScope.launch {
                    _event.emit(HomeEvent.NavigateToDetail(action.id))
                }
            }
            is HomeAction.OnRefresh -> {
                loadLogs()
            }
        }
    }
}
