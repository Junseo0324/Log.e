package com.devhjs.loge.presentation.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devhjs.loge.presentation.component.LogESnackbar

// ... imports

@Composable
fun HomeScreenRoot(
    onNavigateToDetail: (Long) -> Unit,
    onNavigateToWrite: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    snackbarMessage: String? = null,
    onConsumeSnackbarMessage: () -> Unit = {}
) {
    // ViewModel에서 State 수집
    val state by viewModel.state.collectAsStateWithLifecycle()
    
    // Snackbar 상태 관리
    val snackbarHostState = remember { SnackbarHostState() }
    
    // 일회성 이벤트 처리
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is HomeEvent.NavigateToDetail -> onNavigateToDetail(event.logId)
                is HomeEvent.NavigateToWrite -> onNavigateToWrite()
                is HomeEvent.ShowError -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    LaunchedEffect(snackbarMessage) {
        if (snackbarMessage != null) {
            snackbarHostState.showSnackbar(snackbarMessage)
            onConsumeSnackbarMessage()
        }
    }
    
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                LogESnackbar(data = data)
            }
        }
    ) { paddingValues ->
        // 순수 UI 컴포넌트에 State와 Action 전달
        HomeScreen(
            modifier = Modifier.padding(paddingValues),
            state = state,
            onAction = viewModel::onAction
        )
    }
}
