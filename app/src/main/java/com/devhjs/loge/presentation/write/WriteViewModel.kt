package com.devhjs.loge.presentation.write

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.model.EmotionType
import com.devhjs.loge.domain.model.Til
import com.devhjs.loge.domain.usecase.AnalyzeLogUseCase
import com.devhjs.loge.domain.usecase.GetTilUseCase
import com.devhjs.loge.domain.usecase.GetTodayLogUseCase
import com.devhjs.loge.domain.usecase.SaveTilUseCase
import com.devhjs.loge.domain.usecase.UpdateTilUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor(
    private val getTilUseCase: GetTilUseCase,
    private val getTodayLogUseCase: GetTodayLogUseCase,
    private val saveTilUseCase: SaveTilUseCase,
    private val updateTilUseCase: UpdateTilUseCase,
    private val analyzeLogUseCase: AnalyzeLogUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(WriteState())
    val state: StateFlow<WriteState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<WriteEvent>()
    val event = _event.asSharedFlow()

    init {
        val logId = savedStateHandle.get<Long>("logId")
        if (logId != null && logId != -1L) {
            loadLog(logId)
        } else {
            checkTodayLog()
        }
    }

    private fun loadLog(logId: Long) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            getTilUseCase(logId).collect { log ->
                _state.update {
                    it.copy(
                        isEditMode = true,
                        originalLogId = log.id,
                        createdAt = log.createdAt,
                        emotionScore = log.emotionScore,
                        emotion = log.emotion,
                        difficultyLevel = log.difficultyLevel,
                        title = log.title,
                        learnings = log.learned,
                        difficulties = log.difficult,
                        aiFeedbackComment = log.aiFeedBack,
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun checkTodayLog() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val todayLog = getTodayLogUseCase().first()

            if (todayLog != null) {
                _state.update {
                    it.copy(
                        isEditMode = true,
                        originalLogId = todayLog.id,
                        createdAt = todayLog.createdAt,
                        emotionScore = todayLog.emotionScore,
                        emotion = todayLog.emotion,
                        difficultyLevel = todayLog.difficultyLevel,
                        title = todayLog.title,
                        learnings = todayLog.learned,
                        difficulties = todayLog.difficult,
                        aiFeedbackComment = todayLog.aiFeedBack,
                        isLoading = false
                    )
                }
            } else {
                _state.update { it.copy(isLoading = false, isEditMode = false) }
            }
        }
    }

    fun onAction(action: WriteAction) {
        when (action) {
            is WriteAction.OnTitleChange -> {
                _state.update { it.copy(title = action.title) }
            }
            is WriteAction.OnLearningsChange -> {
                _state.update { it.copy(learnings = action.learnings) }
            }
            is WriteAction.OnDifficultiesChange -> {
                _state.update { it.copy(difficulties = action.difficulties) }
            }
            is WriteAction.OnSaveClick -> {
                saveLog()
            }
            is WriteAction.OnBackClick -> {
                viewModelScope.launch { _event.emit(WriteEvent.NavigateBack) }
            }
            is WriteAction.OnAiAnalyzeClick -> {
                analyzeLog()
            }
            is WriteAction.OnConsumeError -> {
                _state.update { it.copy(errorMessage = null) }
            }
        }
    }

    private fun analyzeLog() {
        val currentState = _state.value
        if (currentState.isLoading) return
        if (currentState.title.isBlank() || currentState.learnings.isBlank()) {
             viewModelScope.launch {
                 _event.emit(WriteEvent.ShowError("제목과 학습 내용을 입력해주세요."))
             }
             return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            
            val result = analyzeLogUseCase(
                title = currentState.title,
                learned = currentState.learnings,
                difficult = currentState.difficulties
            )

            when (result) {
                is Result.Success -> {
                    val report = result.data
                    _state.update {
                        it.copy(
                            isLoading = false,
                            showAiAnalysisResult = true,
                            emotion = EmotionType.fromString(report.emotion),
                            emotionScore = report.emotionScore,
                            difficultyLevel = mapDifficultyLevel(report.difficultyLevel),
                            aiFeedbackComment = report.comment
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
                    _event.emit(WriteEvent.ShowError("AI 분석에 실패했습니다: ${error.message}"))
                }
            }
        }
    }

    private fun mapDifficultyLevel(level: String): Int {
        return when (level) {
            "쉬움" -> 1
            "보통" -> 2
            "어려움" -> 3
            "매우 어려움" -> 4
            else -> 2
        }
    }

    private fun saveLog() {
        val currentState = _state.value
        if (currentState.title.isBlank() || currentState.learnings.isBlank()) {
            viewModelScope.launch {
                _event.emit(WriteEvent.ShowError("제목과 학습 내용을 입력해주세요."))
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val til = Til(
                id = currentState.originalLogId,
                createdAt = currentState.createdAt,
                title = currentState.title,
                learned = currentState.learnings,
                difficult = currentState.difficulties,
                emotionScore = currentState.emotionScore,
                emotion = currentState.emotion,
                difficultyLevel = currentState.difficultyLevel,
                updatedAt = System.currentTimeMillis(),
                aiFeedBack = currentState.aiFeedbackComment
            )

            val result = if (currentState.isEditMode) {
                 updateTilUseCase(til)
            } else {
                 saveTilUseCase(til)
            }

            when (result) {
                is Result.Success -> {
                    _state.update { it.copy(isLoading = false) }
                    val message = if (currentState.isEditMode) "수정되었습니다." else "저장되었습니다."
                    _event.emit(WriteEvent.SubmitSuccess(message))
                }
                is Result.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.error.message
                        )
                    }
                    _event.emit(WriteEvent.ShowError(result.error.message ?: "저장에 실패했습니다."))
                }
            }
        }
    }
}
