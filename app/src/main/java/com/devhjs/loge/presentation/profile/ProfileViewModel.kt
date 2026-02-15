package com.devhjs.loge.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhjs.loge.core.util.Result

import com.devhjs.loge.domain.usecase.GetGithubStatusUseCase
import com.devhjs.loge.domain.usecase.GetUserUseCase
import com.devhjs.loge.domain.usecase.SaveUserUseCase
import com.devhjs.loge.domain.usecase.SignInWithGithubUseCase
import com.devhjs.loge.domain.usecase.SignOutGithubUseCase
import com.devhjs.loge.domain.usecase.SyncTilsUseCase
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
    private val saveUserUseCase: SaveUserUseCase,
    private val signInWithGithubUseCase: SignInWithGithubUseCase,
    private val signOutGithubUseCase: SignOutGithubUseCase,
    private val getGithubStatusUseCase: GetGithubStatusUseCase,
    private val syncTilsUseCase: SyncTilsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<ProfileEvent>()
    val event = _event.asSharedFlow()

    init {
        loadUser()
        loadGithubStatus()
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

    // GitHub 연결 상태를 확인해 State 반영
    private fun loadGithubStatus() {
        val status = getGithubStatusUseCase()
        _state.update {
            it.copy(
                isGithubConnected = status.isConnected,
                githubUsername = status.username,
                githubAvatarUrl = status.avatarUrl
            )
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
            is ProfileAction.OnGithubLoginClick -> {
                signInWithGithub()
            }
            is ProfileAction.OnGithubDisconnectClick -> {
                disconnectGithub()
            }
        }
    }

    private fun signInWithGithub() {
        viewModelScope.launch {
            when (val result = signInWithGithubUseCase()) {
                is Result.Success -> {
                    // 로그인 성공 후 상태 갱신
                    loadGithubStatus()
                    // 로컬 User 데이터를 Supabase에 동기화
                    syncLocalUserToRemote()
                    // 로컬 TIL 데이터를 Supabase에 일괄 동기화
                    syncLocalTilsToRemote()
                    _event.emit(ProfileEvent.ShowSnackbar("GitHub 연동 성공!"))
                }
                is Result.Error -> {
                    _event.emit(ProfileEvent.ShowSnackbar("GitHub 연동에 실패했습니다: ${result.error.message}"))
                }
            }
        }
    }

    private fun disconnectGithub() {
        viewModelScope.launch {
            when (val result = signOutGithubUseCase()) {
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            isGithubConnected = false,
                            githubUsername = null,
                            githubAvatarUrl = null
                        )
                    }
                    _event.emit(ProfileEvent.ShowSnackbar("GitHub 연결이 해제되었습니다."))
                }
                is Result.Error -> {
                    _event.emit(ProfileEvent.ShowSnackbar("연결 해제에 실패했습니다."))
                }
            }
        }
    }

    // GitHub 로그인 성공 후 로컬 User 데이터를 Supabase에 동기화
    private suspend fun syncLocalUserToRemote() {
        val currentUser = _state.value.user
        when (val result = saveUserUseCase(currentUser)) {
            is Result.Success -> { }
            is Result.Error -> {
                _event.emit(ProfileEvent.ShowSnackbar("프로필 동기화에 실패했습니다."))
            }
        }
    }

    // GitHub 로그인 성공 후 로컬 TIL 데이터를 Supabase에 일괄 동기화
    private suspend fun syncLocalTilsToRemote() {
        when (val result = syncTilsUseCase()) {
            is Result.Success -> { }
            is Result.Error -> {
                _event.emit(ProfileEvent.ShowSnackbar("TIL 동기화에 실패했습니다."))
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
