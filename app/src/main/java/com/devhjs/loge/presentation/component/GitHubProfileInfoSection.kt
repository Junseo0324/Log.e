package com.devhjs.loge.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devhjs.loge.R
import com.devhjs.loge.presentation.profile.ProfileState

/**
 * GitHub 연동 시 표시되는 읽기 전용 프로필 정보 섹션
 */
@Composable
fun GitHubProfileInfoSection(
    state: ProfileState
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // 이름
        ProfileInfoRow(
            label = "이름",
            value = state.user.name,
            iconRes = R.drawable.ic_profile
        )

        Spacer(modifier = Modifier.height(16.dp))

        // GitHub ID
        ProfileInfoRow(
            label = "GitHub",
            value = state.user.githubId,
            iconRes = R.drawable.ic_github
        )


    }
}