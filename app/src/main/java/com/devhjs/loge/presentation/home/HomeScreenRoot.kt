package com.devhjs.loge.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

/**
 * HomeScreenRoot - ViewModel 연결 및 Navigation 처리를 담당하는 컴포넌트
 * 상태 호이스팅을 적용하여 Navigation 콜백을 외부로 노출
 */
@Composable
fun HomeScreenRoot(
    onNavigateToDetail: (Long) -> Unit,
    onNavigateToWrite: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    // ViewModel에서 State 수집
    val state by viewModel.state.collectAsStateWithLifecycle()
    
    // 일회성 이벤트 처리
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is HomeEvent.NavigateToDetail -> onNavigateToDetail(event.logId)
                is HomeEvent.NavigateToWrite -> onNavigateToWrite()
                is HomeEvent.ShowError -> {
                    // TODO: Snackbar 표시
                }
            }
        }
    }
    
    // 순수 UI 컴포넌트에 State와 Action 전달
    HomeScreen(
        state = state,
        action = HomeAction(
            onAddClick = viewModel::onAddClick,
            onLogClick = viewModel::onLogClick,
            onRefresh = viewModel::loadLogs
        )
    )
}
