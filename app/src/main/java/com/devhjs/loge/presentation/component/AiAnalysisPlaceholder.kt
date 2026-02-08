package com.devhjs.loge.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devhjs.loge.presentation.designsystem.AppColors
import com.devhjs.loge.presentation.designsystem.AppTextStyles

@Composable
fun AiAnalysisPlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.cardBackground.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
            .border(1.dp, AppColors.primary.copy(alpha = 0.2f), RoundedCornerShape(10.dp))
            .padding(16.dp)
    ) {
        Row {
            Text(
                text = "//",
                style = AppTextStyles.Pretendard.Body.copy(color = AppColors.iconPrimary)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "AI 분석으로 감정, 난이도, 피드백을 자동으로 받아보세요",
                style = AppTextStyles.Pretendard.Body.copy(color = AppColors.subTextColor)
            )
        }
    }
}

@Preview
@Composable
private fun AiAnalysisPlaceholderPreview() {
    AiAnalysisPlaceholder()
}