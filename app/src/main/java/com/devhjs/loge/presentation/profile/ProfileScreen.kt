package com.devhjs.loge.presentation.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devhjs.loge.presentation.component.GitHubConnectionCard
import com.devhjs.loge.presentation.component.ProfileEditCard

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    state: ProfileState = ProfileState(),
    onAction: (ProfileAction) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // 프로필 편집 카드
        ProfileEditCard(state = state, onAction = onAction)

        Spacer(modifier = Modifier.height(16.dp))

        // GitHub 연동 카드
        GitHubConnectionCard(state = state, onAction = onAction)

        Spacer(modifier = Modifier.height(24.dp))
    }
}


@Preview
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen()
}

@Preview
@Composable
private fun ProfileScreenConnectedPreview() {
    ProfileScreen(
        state = ProfileState(
            isGithubConnected = true,
            githubUsername = "Junseo0324"
        )
    )
}