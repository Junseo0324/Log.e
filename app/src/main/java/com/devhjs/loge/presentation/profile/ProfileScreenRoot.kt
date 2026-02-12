package com.devhjs.loge.presentation.profile

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devhjs.loge.R
import com.devhjs.loge.presentation.component.CustomAppBar
import com.devhjs.loge.presentation.component.CustomButton
import com.devhjs.loge.presentation.designsystem.AppColors

@Composable
fun ProfileScreenRoot(
    onBackClick: () -> Unit,
    onSubmitSuccess: (String) -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel.event) {
        viewModel.event.collect { event ->
            when (event) {
                is ProfileEvent.NavigateBack -> {
                    onBackClick()
                }

                is ProfileEvent.SubmitSuccess -> {
                    onSubmitSuccess(event.message)
                }

                is ProfileEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            CustomAppBar(
                title = "프로필 수정",
                titleIcon = R.drawable.ic_edit,
                onBackClick = { viewModel.onAction(ProfileAction.OnBackClick) },
                actions = {
                    CustomButton(
                        modifier = Modifier
                            .width(68.dp)
                            .padding(end = 4.dp),
                        backgroundColor = AppColors.primary,
                        icon = R.drawable.ic_save,
                        text = "저장",
                        contentColor = AppColors.black,
                        onClick = { viewModel.onAction(ProfileAction.OnSaveClick) }
                    )
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = AppColors.background
    ) { paddingValues ->
        ProfileScreen(
            state = state,
            onAction = viewModel::onAction,
            modifier = Modifier.padding(paddingValues)
        )
    }
}