package com.devhjs.loge.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.devhjs.loge.R
import com.devhjs.loge.presentation.designsystem.AppColors
import com.devhjs.loge.presentation.profile.ProfileAction
import com.devhjs.loge.presentation.profile.ProfileState

/**
 * 프로필 편집 카드
 * - 로컬 모드: 이름만 편집 가능, 기본 아이콘 표시
 * - GitHub 연동 모드: GitHub 아바타/이름/GitHub ID/이메일 읽기 전용 표시
 */
@Composable
fun ProfileEditCard(
    state: ProfileState = ProfileState(),
    onAction: (ProfileAction) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.cardBackground, RoundedCornerShape(10.dp))
            .padding(24.dp),
    ) {
        // 프로필 아이콘 영역
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if (state.isGithubConnected && state.githubAvatarUrl != null) {
                // GitHub 연동 시: 아바타 이미지 표시
                AsyncImage(
                    model = state.githubAvatarUrl,
                    contentDescription = "GitHub 프로필 사진",
                    modifier = Modifier
                        .size(96.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                // 로컬일 경우
                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(AppColors.primary, AppColors.gradient2)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_profile),
                        contentDescription = null,
                        tint = AppColors.white,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        if (state.isGithubConnected) {
            // GitHub 연동
            GitHubProfileInfoSection(state = state)
        } else {
            // 로컬: 이름만 편집 가능
            CustomTextField(
                value = state.user.name,
                onValueChange = { onAction(ProfileAction.OnNameChange(it)) },
                label = "이름",
                placeholder = "이름을 입력하세요",
                leadingIconRes = R.drawable.ic_profile
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}
