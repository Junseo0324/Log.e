package com.devhjs.loge.presentation.write

import android.app.Activity
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devhjs.loge.R
import com.devhjs.loge.presentation.component.CustomAppBar
import com.devhjs.loge.presentation.component.CustomButton
import com.devhjs.loge.presentation.component.CustomDialog
import com.devhjs.loge.presentation.component.LogESnackbar
import com.devhjs.loge.presentation.component.RewardAdManager
import com.devhjs.loge.presentation.designsystem.AppColors

@Composable
fun WriteScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: WriteViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onSubmitSuccess: (String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    val rewardAdManager = remember {
        (context as? Activity)?.let { RewardAdManager(it) }
    }

    // 광고 시청 여부 확인 다이얼로그 표시 여부
    var showAdConfirmDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        rewardAdManager?.loadAd()
    }

    LaunchedEffect(viewModel.event) {
        viewModel.event.collect { writeEvent ->
            when (writeEvent) {
                is WriteEvent.NavigateBack -> onBackClick()
                is WriteEvent.SubmitSuccess -> onSubmitSuccess(writeEvent.message)
                is WriteEvent.ShowError -> {
                    snackbarHostState.showSnackbar(writeEvent.message)
                }
                is WriteEvent.ShowRewardAdDialog -> {
                    // 광고 바로 노출 금지 → 먼저 확인 다이얼로그 표시
                    showAdConfirmDialog = true
                }
            }
        }
    }

    // 광고 시청 확인 다이얼로그
    if (showAdConfirmDialog) {
        CustomDialog(
            title = "광고 시청 후 AI 분석",
            description = "오늘의 AI 분석을 이미 사용했어요.\n짧은 광고를 시청하면 한 번 더 분석할 수 있어요.",
            confirmText = "광고 보기",
            dismissText = "취소",
            onConfirm = {
                showAdConfirmDialog = false
                rewardAdManager?.showAd(
                    onRewarded = { viewModel.onAction(WriteAction.OnAiAnalyzeAfterAd) },
                    onFailed = {}
                )
            },
            onDismiss = {
                showAdConfirmDialog = false
            }
        )
    }

    Scaffold(
        topBar = {
            CustomAppBar(
                title = if (state.isEditMode) "로그 수정" else "새 로그",
                titleIcon = R.drawable.ic_write_filled,
                onBackClick = { viewModel.onAction(WriteAction.OnBackClick) },
                actions = {
                    CustomButton(
                        modifier = Modifier
                            .width(68.dp)
                            .padding(end = 4.dp),
                        backgroundColor = AppColors.primary,
                        icon = R.drawable.ic_save,
                        text = "저장",
                        contentColor = AppColors.black,
                        onClick = { viewModel.onAction(WriteAction.OnSaveClick) }
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
        WriteScreen(
            state = state,
            onAction = viewModel::onAction,
            modifier = Modifier.padding(paddingValues)
        )
    }
}