package com.devhjs.loge.presentation.detail

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devhjs.loge.R
import com.devhjs.loge.presentation.component.CustomAppBar
import com.devhjs.loge.presentation.component.CustomDialog
import com.devhjs.loge.presentation.component.LogESnackbar
import com.devhjs.loge.presentation.designsystem.AppColors

@Composable
fun DetailScreenRoot(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    onNavigateToEdit: (Long) -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is DetailEvent.NavigateBack -> onNavigateBack()
                is DetailEvent.NavigateToEdit -> onNavigateToEdit(event.logId)
                is DetailEvent.ShowDeleteDialog -> {
                    showDeleteDialog = true
                }
                is DetailEvent.ShowError -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    if (showDeleteDialog) {
        CustomDialog(
            title = "로그 TIL 삭제",
            description = "정말로 이 TIL을 삭제하시겠습니까?\n삭제된 데이터는 복구할 수 없습니다.",
            confirmText = "삭제",
            onConfirm = {
                state.log?.id?.let { id ->
                    viewModel.onAction(DetailAction.OnDeleteConfirm(id))
                }
                showDeleteDialog = false
            },
            onDismiss = { showDeleteDialog = false }
        )
    }

    Scaffold(
        topBar = {
            CustomAppBar(
                title = "로그 상세",
                titleIcon = R.drawable.ic_detail,
                onBackClick = { viewModel.onAction(DetailAction.OnBackClick) },
                actions = {
                    val logId = state.log?.id
                    if (logId != null) {
                        IconButton(onClick = { viewModel.onAction(DetailAction.OnEditClick(logId)) }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_edit),
                                contentDescription = "Edit",
                                tint = AppColors.contentTextColor
                            )
                        }
                        IconButton(onClick = { viewModel.onAction(DetailAction.OnDeleteClick(logId)) }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_delete),
                                contentDescription = "Delete",
                                tint = AppColors.red
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
        containerColor = AppColors.background
    ) { paddingValues ->
        DetailScreen(
            state = state,
            modifier = modifier.padding(paddingValues),
        )
    }
}