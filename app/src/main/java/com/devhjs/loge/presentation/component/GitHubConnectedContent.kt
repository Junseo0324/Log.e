package com.devhjs.loge.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.loge.R
import com.devhjs.loge.presentation.designsystem.LogETheme
import com.devhjs.loge.presentation.designsystem.AppTextStyles


/**
 * GitHub 연결됨 상태 UI (Figma 디자인 기반)
 */
@Composable
fun GitHubConnectedContent(
    username: String = "",
    onDisconnect: () -> Unit= {},
) {
    // 연결 상태 정보 박스
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = LogETheme.colors.background,
                shape = RoundedCornerShape(10.dp)
            )
            .border(
                width = 1.dp,
                color = LogETheme.colors.primary.copy(alpha = 0.3f),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_check),
                    contentDescription = null,
                    tint = LogETheme.colors.primary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "연결됨",
                    style = AppTextStyles.Pretendard.Header5.copy(
                        color = LogETheme.colors.primary,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_github),
                contentDescription = null,
                tint = LogETheme.colors.contentTextColor,
                modifier = Modifier.size(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Username
        Row {
            Text(
                text = "Username: ",
                style = AppTextStyles.Pretendard.Body.copy(
                    color = LogETheme.colors.labelTextColor,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
            )
            Text(
                text = username,
                style = AppTextStyles.Pretendard.Body.copy(
                    color = LogETheme.colors.subTextColor,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // 프로필 링크
        Text(
            text = "github.com/$username",
            style = AppTextStyles.Pretendard.Body.copy(
                color = LogETheme.colors.lightBlue,
                fontSize = 12.sp,
                lineHeight = 16.sp
            )
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    // "연결 해제" 버튼
    Button(
        onClick = onDisconnect,
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        border = BorderStroke(
            width = 1.dp,
            color = LogETheme.colors.red.copy(alpha = 0.3f)
        )
    ) {
        Text(
            text = "연결 해제",
            style = AppTextStyles.Pretendard.Header5.copy(
                color = LogETheme.colors.red,
                fontSize = 14.sp
            )
        )
    }
}

@Preview
@Composable
private fun GitHubConnectedContentPreview() {
    GitHubConnectedContent()
}