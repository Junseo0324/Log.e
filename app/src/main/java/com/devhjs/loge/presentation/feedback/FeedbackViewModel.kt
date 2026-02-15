package com.devhjs.loge.presentation.feedback

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.model.Feedback
import com.devhjs.loge.domain.usecase.SendFeedbackUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedbackViewModel @Inject constructor(
    private val sendFeedbackUseCase: SendFeedbackUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FeedbackState())
    val state: StateFlow<FeedbackState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<FeedbackEvent>()
    val event = _event.asSharedFlow()

    fun onAction(action: FeedbackAction) {
        when (action) {
            is FeedbackAction.OnTypeSelected -> {
                _state.update { it.copy(type = action.type) }
            }
            is FeedbackAction.OnTitleChanged -> {
                _state.update { it.copy(title = action.title) }
            }
            is FeedbackAction.OnContentChanged -> {
                _state.update { it.copy(content = action.content) }
            }
            is FeedbackAction.OnSubmit -> submitFeedback()
            is FeedbackAction.OnCancel -> {
                viewModelScope.launch {
                    _event.emit(FeedbackEvent.SubmitSuccess)
                }
            }
        }
    }

    private fun submitFeedback() {
        val currentState = _state.value

        // 유효성 검사
        if (currentState.title.isBlank()) {
            viewModelScope.launch {
                _event.emit(FeedbackEvent.ShowSnackbar("제목을 입력해주세요."))
            }
            return
        }
        if (currentState.content.isBlank()) {
            viewModelScope.launch {
                _event.emit(FeedbackEvent.ShowSnackbar("내용을 입력해주세요."))
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val feedback = Feedback(
                type = currentState.type,
                title = currentState.title,
                content = currentState.content
            )

            when (val result = sendFeedbackUseCase(feedback)) {
                is Result.Success -> {
                    _event.emit(FeedbackEvent.SubmitSuccess)
                }
                is Result.Error -> {
                    _event.emit(FeedbackEvent.ShowSnackbar("피드백 전송에 실패했습니다."))
                }
            }

            _state.update { it.copy(isLoading = false) }
        }
    }
}
