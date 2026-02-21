package com.devhjs.loge.presentation.stat

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devhjs.loge.R
import com.devhjs.loge.presentation.component.CustomDialog
import com.devhjs.loge.presentation.component.LogESnackbar
import com.devhjs.loge.presentation.component.LogETopBar
import com.devhjs.loge.presentation.component.RewardAdManager
import com.devhjs.loge.presentation.designsystem.AppColors
import com.devhjs.loge.presentation.designsystem.AppTextStyles

@Composable
fun StatScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: StatViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    // 리워드 광고 매니저 (Activity에서만 동작)
    val rewardAdManager = remember {
        (context as? Activity)?.let { RewardAdManager(it) }
    }

    // 광고 시청 확인 다이얼로그 표시 여부
    var showAdConfirmDialog by remember { mutableStateOf(false) }

    // 리워드 광고 미리 로드
    LaunchedEffect(Unit) {
        rewardAdManager?.loadAd()
    }

    // 일회성 이벤트 수집
    LaunchedEffect(viewModel.event) {
        viewModel.event.collect { statEvent ->
            when (statEvent) {
                is StatEvent.ShowError -> {
                    snackbarHostState.showSnackbar(statEvent.message)
                }
                is StatEvent.ShowMonthlyRewardAd -> {
                    // 광고 바로 노출 금지 → 확인 다이얼로그 먼저 표시
                    showAdConfirmDialog = true
                }
            }
        }
    }

    // 광고 시청 확인 다이얼로그
    if (showAdConfirmDialog) {
        CustomDialog(
            title = "광고 시청 후 재분석",
            description = "이번 달 AI 분석을 이미 완료했어요.\n짧은 광고를 시청하면 한 번 더 분석할 수 있어요.",
            confirmText = "광고 보기",
            dismissText = "취소",
            onConfirm = {
                showAdConfirmDialog = false
                if (rewardAdManager != null) {
                    rewardAdManager.showAd(
                        onRewarded = { viewModel.onAction(StatAction.OnAiReAnalyzeAfterAd) },
                        onFailed = {}
                    )
                }
            },
            onDismiss = { showAdConfirmDialog = false }
        )
    }

    Scaffold(
        topBar = {
            LogETopBar(
                title = "통계 분석",
                titleIcon = R.drawable.ic_stat_filled,
                bottomContent = {
                    // 월 네비게이션 UI
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { viewModel.onAction(StatAction.OnPreviousMonthClick) }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_back),
                                contentDescription = "이전 달",
                                tint = AppColors.contentTextColor
                            )
                        }
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                // "yyyy-MM" → "yyyy.MM" 표시 형식으로 변환
                                text = state.selectedMonth.replace("-", "."),
                                style = AppTextStyles.Pretendard.Header3.copy(color = AppColors.titleTextColor)
                            )
                        }
                        IconButton(onClick = { viewModel.onAction(StatAction.OnNextMonthClick) }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_forward),
                                contentDescription = "다음 달",
                                tint = AppColors.contentTextColor,
                            )
                        }
                    }
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
        StatScreen(
            state = state,
            onAction = viewModel::onAction,
            modifier = Modifier.padding(paddingValues)
        )
    }
}