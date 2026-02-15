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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.loge.R
import com.devhjs.loge.presentation.designsystem.AppColors
import com.devhjs.loge.presentation.designsystem.AppTextStyles
import com.devhjs.loge.presentation.profile.ProfileAction
import com.devhjs.loge.presentation.profile.ProfileState

/**
 * 기존 프로필 편집 카드 (이름, GitHub ID 입력)
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
        // 프로필 아이콘
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
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

        Spacer(modifier = Modifier.height(32.dp))

        // 이름 입력
        CustomTextField(
            value = state.user.name,
            onValueChange = { onAction(ProfileAction.OnNameChange(it)) },
            label = "이름",
            placeholder = "이름을 입력하세요",
            leadingIconRes = R.drawable.ic_profile
        )

        Spacer(modifier = Modifier.height(24.dp))

        // GitHub ID 입력
        CustomTextField(
            value = state.user.githubId,
            onValueChange = { onAction(ProfileAction.OnGithubIdChange(it)) },
            label = "GitHub",
            placeholder = "username",
            leadingIconRes = R.drawable.ic_github,
            prefix = {
                Text(
                    text = "github.com/",
                    style = AppTextStyles.JetBrain.Label.copy(
                        color = AppColors.subTextColor,
                        fontSize = 14.sp
                    )
                )
            }
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}