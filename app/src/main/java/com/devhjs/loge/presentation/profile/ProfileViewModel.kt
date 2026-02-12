package com.devhjs.loge.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.model.User
import com.devhjs.loge.domain.usecase.GetUserUseCase
import com.devhjs.loge.domain.usecase.SaveUserUseCase
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
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val saveUserUseCase: SaveUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<ProfileEvent>()
    val event = _event.asSharedFlow()

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            getUserUseCase().collect { result ->
                when (result) {
                    is Result.Success -> {
                        _state.update { 
                            it.copy(
                                user = result.data,
                                isLoading = false
                            ) 
                        }
                    }
                    is Result.Error -> {
                        _state.update { it.copy(isLoading = false) }
                        _event.emit(ProfileEvent.ShowSnackbar("프로필을 불러오지 못했습니다."))
                    }
                }
            }
        }
    }

    fun onAction(action: ProfileAction) {
        when (action) {
            is ProfileAction.OnNameChange -> {
                _state.update { 
                    it.copy(user = it.user.copy(name = action.name)) 
                }
            }
            is ProfileAction.OnGithubIdChange -> {
                _state.update { 
                    it.copy(user = it.user.copy(githubId = action.githubId)) 
                }
            }
            is ProfileAction.OnSaveClick -> {
                saveUser()
            }
            is ProfileAction.OnBackClick -> {
                viewModelScope.launch { _event.emit(ProfileEvent.NavigateBack) }
            }
        }
    }

    private fun saveUser() {
        val currentUser = _state.value.user
        if (currentUser.name.isBlank()) {
            viewModelScope.launch {
                _event.emit(ProfileEvent.ShowSnackbar("이름을 입력해주세요."))
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val result = saveUserUseCase(currentUser)) {
                is Result.Success -> {
                    _state.update { it.copy(isLoading = false) }
                    _event.emit(ProfileEvent.SubmitSuccess("저장되었습니다."))
                }
                is Result.Error -> {
                    _state.update { it.copy(isLoading = false) }
                    _event.emit(ProfileEvent.ShowSnackbar("저장에 실패했습니다: ${result.error.message}"))
                }
            }
        }
    }
}
