package com.devhjs.loge.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devhjs.loge.R
import com.devhjs.loge.presentation.designsystem.LogETheme
import com.devhjs.loge.presentation.designsystem.AppTextStyles

@Composable
fun AiAnalyzeButton(
    isLoading: Boolean = false,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        LogETheme.colors.gradient1,
                        LogETheme.colors.gradient2,
                    )
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .clickable(enabled = !isLoading) { onClick() }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    color = LogETheme.colors.white,
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "분석중...",
                    style = AppTextStyles.Pretendard.Label,
                    color = LogETheme.colors.white
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.ic_ai),
                    contentDescription = null,
                    tint = LogETheme.colors.white,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "ai 분석하기",
                    style = AppTextStyles.Pretendard.Label,
                    color = LogETheme.colors.white
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AiAnalyzeButtonPreview() {
    AiAnalyzeButton()
}


@Preview(showBackground = true)
@Composable
private fun AiAnalyzeButtonPreviewIsLoading() {
    AiAnalyzeButton(isLoading = true)
}