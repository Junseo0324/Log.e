package com.devhjs.loge.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devhjs.loge.R
import com.devhjs.loge.domain.model.AiReport
import com.devhjs.loge.presentation.designsystem.AppColors
import com.devhjs.loge.presentation.designsystem.AppTextStyles

@Composable
fun MonthlyReviewSection(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    aiReport: AiReport?,
    onAnalyzeClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(AppColors.cardBackground)
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "월간 회고",
                style = AppTextStyles.Pretendard.Header2,
                color = AppColors.titleTextColor
            )

            if (!isLoading) {
                CustomButton(
                    modifier = Modifier.width(78.dp),
                    text = "생성",
                    icon = R.drawable.ic_ai,
                    backgroundColor = AppColors.primary,
                    contentColor = AppColors.white,
                    onClick = onAnalyzeClick,
                    contentDescription = if (aiReport == null) "Generate" else "Regenerate"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = AppColors.primary,
                    modifier = Modifier.size(24.dp)
                )
            }
        } else if (aiReport != null) {
            Spacer(modifier = Modifier.height(4.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = aiReport.comment,
                style = AppTextStyles.Pretendard.Body,
                color = AppColors.contentTextColor
            )
        } else {
            Text(
                text = "// AI가 월간 진행 상황을 분석합니다",
                style = AppTextStyles.Pretendard.Body,
                color = AppColors.contentTextColor
            )
        }
    }
}

@Preview
@Composable
private fun MonthlyReviewSectionInitialPreview() {
    MonthlyReviewSection(
        isLoading = false,
        aiReport = null,
        onAnalyzeClick = {}
    )
}

@Preview
@Composable
private fun MonthlyReviewSectionLoadingPreview() {
    MonthlyReviewSection(
        isLoading = true,
        aiReport = null,
        onAnalyzeClick = {}
    )
}

@Preview
@Composable
private fun MonthlyReviewSectionResultPreview() {
    val mockReport = AiReport(
        date = System.currentTimeMillis(),
        emotion = "만족",
        emotionScore = 80,
        difficultyLevel = "보통",
        comment = "이번 달은 꾸준히 학습하셨네요! ‘기쁨’ 감정을 주로 느끼며, 안정적인 학습 패턴을 보이고 있습니다. 다음 달에도 이 기세를 이어가세요!"
    )
    MonthlyReviewSection(
        isLoading = false,
        aiReport = mockReport,
        onAnalyzeClick = {}
    )
}