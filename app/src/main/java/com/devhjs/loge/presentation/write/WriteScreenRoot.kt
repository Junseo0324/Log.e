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
import com.devhjs.loge.presentation.designsystem.LogETheme

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

    LaunchedEffect(viewModel.event) {
        viewModel.event.collect { writeEvent ->
            when (writeEvent) {
                is WriteEvent.NavigateBack -> onBackClick()
                is WriteEvent.SubmitSuccess -> onSubmitSuccess(writeEvent.message)
                is WriteEvent.ShowError -> {
                    snackbarHostState.showSnackbar(writeEvent.message)
                }
            }
        }
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
                        backgroundColor = LogETheme.colors.primary,
                        icon = R.drawable.ic_save,
                        text = "저장",
                        contentColor = LogETheme.colors.black,
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
        containerColor = LogETheme.colors.background,
        modifier = modifier
    ) { paddingValues ->
        WriteScreen(
            state = state,
            onAction = viewModel::onAction,
            modifier = Modifier.padding(paddingValues)
        )
    }
}