package com.devhjs.loge.presentation.setting

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devhjs.loge.R
import com.devhjs.loge.presentation.component.LogESnackbar
import com.devhjs.loge.presentation.component.LogETopBar
import com.devhjs.loge.presentation.designsystem.AppColors
import com.devhjs.loge.presentation.designsystem.AppTextStyles

@Composable
fun SettingScreenRoot(
    onNavigateToLicenses: () -> Unit,
    onNavigateToProfileEdit: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = hiltViewModel(),
    snackbarMessage: String? = null,
    onConsumeSnackbarMessage: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(snackbarMessage) {
        if (snackbarMessage != null) {
            snackbarHostState.showSnackbar(snackbarMessage)
            onConsumeSnackbarMessage()
        }
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
                    snackbarHostState.showSnackbar(event.message)
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
                        text = "// 앱 환경설정 및 데이터 관리",
                        style = AppTextStyles.JetBrain.Label.copy(color = AppColors.labelTextColor, fontSize = 12.sp),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                LogESnackbar(data = data)
            }
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
}