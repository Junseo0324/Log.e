package com.devhjs.loge.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.usecase.DeleteAllUserDataUseCase
import com.devhjs.loge.domain.usecase.ExportTilUseCase
import com.devhjs.loge.domain.usecase.GetUserUseCase
import com.devhjs.loge.domain.usecase.UpdateNotificationSettingUseCase
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val updateNotificationSettingUseCase: UpdateNotificationSettingUseCase, // Changed
    private val deleteAllUserDataUseCase: DeleteAllUserDataUseCase,
    private val exportTilUseCase: ExportTilUseCase
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
                if (action.enabled) {
                    viewModelScope.launch { _event.emit(SettingEvent.RequestNotificationPermission) }
                } else {
                    updateNotificationSetting(false)
                }
            }
            is SettingAction.OnProfileClick -> {
                viewModelScope.launch { _event.emit(SettingEvent.NavigateToProfile) }
            }
            is SettingAction.OnLicensesClick -> {
                viewModelScope.launch { _event.emit(SettingEvent.NavigateToLicenses) }
            }

            is SettingAction.OnExportClick -> {
                 viewModelScope.launch { 
                     val date = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
                     val fileName = "LogE_Backup_$date.csv"
                     _event.emit(SettingEvent.LaunchExport(fileName))
                 }
            }
            is SettingAction.OnExportUriSelected -> {
                viewModelScope.launch {
                    _state.update { it.copy(isExporting = true) }
                    when (val result = exportTilUseCase(action.uri)) {
                        is Result.Success -> {
                            _event.emit(SettingEvent.ShowSnackbar("CSV 파일이 저장되었습니다."))
                        }
                        is Result.Error -> {
                            _event.emit(SettingEvent.ShowSnackbar("파일 저장에 실패했습니다: ${result.error.message}"))
                        }
                    }
                    _state.update { it.copy(isExporting = false) }
                }
            }
            is SettingAction.OnDeleteAllClick -> {
                 _state.update { it.copy(showDeleteDialog = true) }
            }
            is SettingAction.OnDeleteConfirm -> {
                viewModelScope.launch {
                    when (deleteAllUserDataUseCase()) {
                        is Result.Success -> {
                            _state.update { it.copy(showDeleteDialog = false) }
                            _event.emit(SettingEvent.ShowSnackbar("모든 데이터가 삭제되었습니다."))
                        }
                        is Result.Error -> {
                            _state.update { it.copy(showDeleteDialog = false) }
                            _event.emit(SettingEvent.ShowSnackbar("데이터 삭제에 실패했습니다."))
                        }
                    }
                }
            }
            is SettingAction.OnDeleteDismiss -> {
                _state.update { it.copy(showDeleteDialog = false) }
            }
            is SettingAction.OnTimeSelected -> {
                updateNotificationTime(action.hour, action.minute)
                _state.update { it.copy(isTimePickerVisible = false) }
            }
            is SettingAction.OnTimePickerClick -> {
                _state.update { it.copy(isTimePickerVisible = true) }
            }
            is SettingAction.OnTimePickerDismiss -> {
                _state.update { it.copy(isTimePickerVisible = false) }
            }
            is SettingAction.OnFeedbackClick -> {
                viewModelScope.launch { _event.emit(SettingEvent.NavigateToFeedback) }
            }
        }
    }

    fun onNotificationPermissionResult(isGranted: Boolean) {
        if (isGranted) {
            updateNotificationSetting(true)
        } else {
            viewModelScope.launch {
                _event.emit(SettingEvent.ShowSnackbar("알림 권한이 필요합니다."))
            }
            updateNotificationSetting(false)
        }
    }

    // 알림 설정 변경
    private fun updateNotificationSetting(enabled: Boolean) {
        val currentUser = _state.value.user
        val newUser = currentUser.copy(isNotificationEnabled = enabled)
        
        viewModelScope.launch {
            updateNotificationSettingUseCase(newUser)
        }
    }

    // 알림 시간 업데이트
    private fun updateNotificationTime(hour: Int, minute: Int) {
        val currentUser = _state.value.user
        val newUser = currentUser.copy(notificationTime = Pair(hour, minute))

        viewModelScope.launch {
            updateNotificationSettingUseCase(newUser)
        }
    }
}
