package com.devhjs.loge.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.loge.R
import com.devhjs.loge.presentation.designsystem.AppColors
import com.devhjs.loge.presentation.designsystem.AppTextStyles

/**
 * GitHub 미연결 상태 UI (사용자 첨부 이미지 기반)
 */
@Composable
fun GitHubDisconnectedContent(
    onConnect: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = AppColors.background,
                shape = RoundedCornerShape(10.dp)
            )
            .border(
                width = 1.dp,
                color = AppColors.primary.copy(alpha = 0.3f),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "⚠️",
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "GitHub 연동 시 다음 혜택을 받을 수 있습니다:",
                style = AppTextStyles.Pretendard.Body.copy(
                    color = AppColors.subTextColor,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 혜택 목록
        val benefits = listOf(
            "클라우드 동기화로 어디서나 접근",
            "GitHub 프로필과 연동",
            "학습 기록 백업 및 복원",
        )
        benefits.forEach { benefit ->
            Row(
                modifier = Modifier.padding(start = 8.dp, bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "→",
                    style = AppTextStyles.Pretendard.Body.copy(
                        color = AppColors.primary,
                        fontSize = 12.sp
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = benefit,
                    style = AppTextStyles.Pretendard.Body.copy(
                        color = AppColors.contentTextColor,
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Button(
        onClick = onConnect,
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .border(
                width = 1.dp,
                color = AppColors.border,
                shape = RoundedCornerShape(8.dp)
            ),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppColors.cardBackground
        )
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_github),
            contentDescription = null,
            tint = AppColors.white,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "GitHub로 계속하기",
            style = AppTextStyles.Pretendard.Header5.copy(
                color = AppColors.white,
                fontSize = 14.sp
            )
        )
    }

    Spacer(modifier = Modifier.height(12.dp))

    // 로그인 없이 사용 가능 안내 문구
    Text(
        text = "로그인 없이도 모든 기능을 사용할 수 있습니다",
        style = AppTextStyles.Pretendard.Body.copy(
            color = AppColors.contentTextColor,
            fontSize = 12.sp,
            lineHeight = 16.sp
        ),
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Preview
@Composable
private fun GithubDisconnectedContentPreview() {
    Column {
        GitHubDisconnectedContent()
    }
}