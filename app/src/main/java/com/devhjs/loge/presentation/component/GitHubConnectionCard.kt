package com.devhjs.loge.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.loge.R
import com.devhjs.loge.presentation.designsystem.AppColors
import com.devhjs.loge.presentation.designsystem.AppTextStyles
import com.devhjs.loge.presentation.profile.ProfileAction
import com.devhjs.loge.presentation.profile.ProfileState

/**
 * GitHub 연동 카드 - 연결 상태에 따라 다른 UI 표시
 */
@Composable
fun GitHubConnectionCard(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.cardBackground, RoundedCornerShape(10.dp))
            .border(
                width = 1.dp,
                color = AppColors.border,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 24.dp, vertical = 24.dp)
    ) {
        // 헤더: GitHub 아이콘 + 제목 + 설명
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // GitHub 아이콘 박스
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .background(
                        color = AppColors.background,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = AppColors.border,
                        shape = RoundedCornerShape(10.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_github),
                    contentDescription = "GitHub",
                    tint = AppColors.white,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = "GitHub 연동",
                    style = AppTextStyles.Pretendard.Header5.copy(
                        color = AppColors.white,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "GitHub 계정을 연결하여 프로필을 동기화하세요",
                    style = AppTextStyles.Pretendard.Body.copy(
                        color = AppColors.contentTextColor,
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (state.isGithubConnected) {
            // 연결됨 상태
            GitHubConnectedContent(
                username = state.githubUsername ?: "",
                onDisconnect = { onAction(ProfileAction.OnGithubDisconnectClick) }
            )
        } else {
            // 미연결 상태
            GitHubDisconnectedContent(
                onConnect = { onAction(ProfileAction.OnGithubLoginClick) }
            )
        }
    }
}