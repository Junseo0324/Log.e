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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.loge.R
import com.devhjs.loge.domain.model.AiReport
import com.devhjs.loge.presentation.designsystem.LogETheme
import com.devhjs.loge.presentation.designsystem.AppTextStyles

@Composable
fun MonthlyReviewSection(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    aiReport: AiReport?,
    onAnalyzeClick: () -> Unit = {},
    canAnalyze: Boolean = true,
    limitMsg: String? = null,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(LogETheme.colors.cardBackground)
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
                color = LogETheme.colors.titleTextColor
            )

            if (!isLoading) {
                // 이미 분석됨: "재생성(광고)" 표시, 미분석: "생성" 표시
                // canAnalyze=false이면 버튼 비활성화 스타일 적용
                val buttonText = when {
                    aiReport != null -> if (canAnalyze) "재생성" else "재생성(광고)"
                    else -> "생성"
                }
                val buttonBg = if (canAnalyze) LogETheme.colors.primary else LogETheme.colors.cardBackground
                val buttonContent = if (canAnalyze) LogETheme.colors.white else LogETheme.colors.subTextColor

                CustomButton(
                    modifier = Modifier.width(if (aiReport != null && !canAnalyze) 100.dp else 78.dp),
                    text = buttonText,
                    icon = R.drawable.ic_ai,
                    backgroundColor = buttonBg,
                    contentColor = buttonContent,
                    onClick = onAnalyzeClick,
                    contentDescription = buttonText
                )
            }
        }

        // 제한 안내 메시지 (데이터 부족 또는 이미 이번 달 분석 완료)
        if (limitMsg != null && !isLoading) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = limitMsg,
                style = AppTextStyles.Pretendard.Label.copy(
                    color = LogETheme.colors.subTextColor,
                    fontSize = 12.sp
                )
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = LogETheme.colors.primary,
                    modifier = Modifier.size(32.dp)
                )
            }
        } else if (aiReport != null) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                val emotionPercent = (aiReport.emotionScore * 20).coerceIn(0, 100)
                SummaryCard(
                    modifier = Modifier.weight(1f),
                    icon = R.drawable.ic_heart,
                    iconColor = LogETheme.colors.pink,
                    title = "평균 감정",
                    mainValue = "$emotionPercent",
                    subValue = "/100",
                    content = {
                        GradientProgressBar(
                            progress = emotionPercent / 100f,
                            gradient = Brush.horizontalGradient(
                                colors = listOf(LogETheme.colors.gradient3, LogETheme.colors.gradient4)
                            )
                        )
                    }
                )

                val difficultyScore = when (aiReport.difficultyLevel) {
                    "쉬움" -> 1.0f
                    "보통" -> 2.5f
                    "어려움" -> 4.0f
                    "매우 어려움" -> 5.0f
                    else -> 2.5f
                }
                SummaryCard(
                    modifier = Modifier.weight(1f),
                    icon = R.drawable.ic_difficulty,
                    iconColor = LogETheme.colors.purple,
                    title = "난이도",
                    mainValue = "$difficultyScore",
                    subValue = "/5",
                    content = {
                        SegmentedProgressBar(
                            maxSteps = 5,
                            currentStep = difficultyScore,
                            activeColor = LogETheme.colors.purple
                        )
                    }
                )

                SummaryCard(
                    modifier = Modifier.weight(1f),
                    icon = R.drawable.ic_normal,
                    iconColor = LogETheme.colors.primary,
                    title = "주요 감정",
                    mainValue = aiReport.emotion,
                    subValue = "",
                    content = {
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = aiReport.comment,
                style = AppTextStyles.Pretendard.Body,
                color = LogETheme.colors.contentTextColor,
                lineHeight = 22.sp
            )

        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                    contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "// AI가 월간 진행 상황을 분석합니다",
                    style = AppTextStyles.Pretendard.Body,
                    color = Color.Gray
                )
            }
        }
    }
}


@Preview
@Composable
private fun MonthlyReviewSectionResultPreview() {
    val mockReport = AiReport(
        date = System.currentTimeMillis(),
        emotion = "기쁨",
        emotionScore = 4, // 80점
        difficultyLevel = "보통", // 2.5점
        comment = "2026년 2월, 총 24일 동안 꾸준히 학습하셨네요! 이번 달에는 주로 '기쁨' 감정을 느끼며 성장하셨습니다.\n\n특히 인상 깊었던 점은 어려운 주제에도 포기하지 않고 도전하는 모습입니다. 난이도 3.2의 주제들을 꾸준히 학습하며 한 단계 성장하셨어요.\n\n다음 달에도 이런 열정을 유지하신다면 더욱 큰 발전을 이룰 수 있을 거예요."
    )
    MonthlyReviewSection(
        isLoading = false,
        aiReport = mockReport
    )
}

@Preview
@Composable
private fun MonthlyReviewSectionInitialPreview() {
    MonthlyReviewSection(
        isLoading = false,
        aiReport = null,
    )
}