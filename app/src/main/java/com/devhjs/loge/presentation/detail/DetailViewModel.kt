package com.devhjs.loge.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.usecase.DeleteTilUseCase
import com.devhjs.loge.domain.usecase.GetTilUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getTilUseCase: GetTilUseCase,
    private val deleteTilUseCase: DeleteTilUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val logId: Long = checkNotNull(savedStateHandle["logId"])

    private val _state = MutableStateFlow(DetailState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<DetailEvent>()
    val event = _event.asSharedFlow()

    init {
        loadLog()
    }

    private fun loadLog() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            getTilUseCase(logId).collect { log ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        log = log
                    )
                }
            }
        }
    }

    fun onAction(action: DetailAction) {
        when (action) {
            is DetailAction.OnBackClick -> {
                viewModelScope.launch {
                    _event.emit(DetailEvent.NavigateBack)
                }
            }
            is DetailAction.OnEditClick -> {
                viewModelScope.launch {
                    _event.emit(DetailEvent.NavigateToEdit(action.id))
                }
            }
            is DetailAction.OnDeleteClick -> {

            }
            is DetailAction.OnDeleteConfirm -> {
                deleteLog()
            }
        }
    }

    private fun deleteLog() {
        viewModelScope.launch {
            val currentLog = _state.value.log ?: return@launch
            when (val result = deleteTilUseCase(currentLog)) {
                is Result.Success -> {
                    _event.emit(DetailEvent.NavigateBack)
                }
                is Result.Error -> {
                    _state.update { it.copy(errorMessage = result.error.message) }
                    _event.emit(DetailEvent.ShowError(result.error.message ?: "삭제 실패"))
                }
            }
        }
    }
}