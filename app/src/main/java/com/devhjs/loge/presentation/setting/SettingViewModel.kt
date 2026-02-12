package com.devhjs.loge.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.usecase.GetUserUseCase
import com.devhjs.loge.domain.usecase.SaveUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val saveUserUseCase: SaveUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SettingState())
    val state: StateFlow<SettingState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<SettingEvent>()
    val event = _event.asSharedFlow()

    init {
        loadUser()
    }

    private fun loadUser() {
        getUserUseCase().onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.update { it.copy(user = result.data) }
                }
                is Result.Error -> {
                    _event.emit(SettingEvent.ShowSnackbar("회원 정보를 불러오는데 실패했습니다."))
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onAction(action: SettingAction) {
        when (action) {
            is SettingAction.OnNotificationToggle -> {
                updateNotificationSetting(action.enabled)
            }
            is SettingAction.OnProfileClick -> {
                viewModelScope.launch { _event.emit(SettingEvent.NavigateToProfile) }
            }
            is SettingAction.OnLicensesClick -> {
                viewModelScope.launch { _event.emit(SettingEvent.NavigateToLicenses) }
            }
            is SettingAction.OnExportClick -> {
                 viewModelScope.launch { 
                     _event.emit(SettingEvent.ShowSnackbar("준비 중인 기능입니다."))
                 }
            }
            is SettingAction.OnDeleteAllClick -> {
                 viewModelScope.launch { 
                     _event.emit(SettingEvent.ShowSnackbar("준비 중인 기능입니다."))
                 }
            }
        }
    }

    private fun updateNotificationSetting(enabled: Boolean) {
        val currentUser = _state.value.user
        val newUser = currentUser.copy(isNotificationEnabled = enabled)
        
        viewModelScope.launch {
            saveUserUseCase(newUser)
        }
    }
}
