package com.devhjs.loge.presentation.setting

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devhjs.loge.R
import com.devhjs.loge.presentation.component.CustomDialog
import com.devhjs.loge.presentation.component.LogETopBar
import com.devhjs.loge.presentation.designsystem.AppColors
import com.devhjs.loge.presentation.designsystem.AppTextStyles

@Composable
fun SettingScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = hiltViewModel(),
    onNavigateToLicenses: () -> Unit,
    onNavigateToProfileEdit: () -> Unit,
    onNavigateToFeedback: () -> Unit,
    onNavigateToOnboarding: () -> Unit,
    onShowSnackbar: (String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val exportLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("text/csv")
    ) { uri ->
        if (uri != null) {
            viewModel.onAction(SettingAction.OnExportUriSelected(uri.toString()))
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        viewModel.onNotificationPermissionResult(isGranted)
    }



    LaunchedEffect(viewModel.event) {
        viewModel.event.collect { event ->
            when (event) {
                is SettingEvent.NavigateToProfile -> {
                    onNavigateToProfileEdit()
                }
                is SettingEvent.NavigateToLicenses -> {
                    onNavigateToLicenses()
                }
                is SettingEvent.ShowSnackbar -> {
                    onShowSnackbar(event.message)
                }
                is SettingEvent.LaunchExport -> {
                    exportLauncher.launch(event.fileName)
                }
                is SettingEvent.RequestNotificationPermission -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    } else {
                        viewModel.onNotificationPermissionResult(true)
                    }
                }
                is SettingEvent.NavigateToFeedback -> {
                    onNavigateToFeedback()
                }
                is SettingEvent.NavigateToOnboarding -> {
                    onNavigateToOnboarding()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            LogETopBar(
                title = "설정",
                titleIcon = R.drawable.setting_outlined,
                bottomContent = {
                    Text(
                        text = "앱 환경설정 및 데이터 관리",
                        style = AppTextStyles.JetBrain.Label.copy(color = AppColors.labelTextColor, fontSize = 12.sp),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            )
        },
        containerColor = AppColors.background,
        modifier = modifier
    ) { paddingValues ->
        SettingScreen(
            state = state,
            onAction = viewModel::onAction,
            modifier = Modifier.padding(paddingValues)
        )
    }

    if (state.showDeleteDialog) {
        CustomDialog(
            title = "모든 데이터 삭제",
            description = "정말로 모든 데이터를 삭제하시겠습니까?\n삭제된 데이터는 복구할 수 없습니다.",
            confirmText = "삭제",
            dismissText = "취소",
            onConfirm = { viewModel.onAction(SettingAction.OnDeleteConfirm) },
            onDismiss = { viewModel.onAction(SettingAction.OnDeleteDismiss) }
        )
    }
}