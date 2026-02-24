package com.devhjs.loge.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.loge.R
import com.devhjs.loge.core.util.DateUtils
import com.devhjs.loge.domain.model.EmotionType
import com.devhjs.loge.domain.model.Til
import com.devhjs.loge.presentation.component.ContentCard
import com.devhjs.loge.presentation.component.InformationChip
import com.devhjs.loge.presentation.component.LevelIndicator
import com.devhjs.loge.presentation.designsystem.AppColors
import com.devhjs.loge.presentation.designsystem.AppTextStyles
import com.devhjs.loge.presentation.util.color
import com.devhjs.loge.presentation.util.iconResId

@Composable
fun DetailScreen(
    state: DetailState,
    modifier: Modifier = Modifier,
) {
    if (state.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = AppColors.primary)
        }
        return
    }

    val log = state.log ?: return

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 날짜 및 시간 섹션
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            InformationChip(
                icon = R.drawable.ic_date,
                text = DateUtils.formatToDate(log.updatedAt)
            )
            InformationChip(
                icon = R.drawable.ic_detail,
                text = DateUtils.formatToTime(log.updatedAt)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 감정 섹션 (커스텀 대형 UI)
        Box(
            modifier = Modifier
                .background(
                    color = log.emotion.color.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(32.dp)
                )
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = log.emotion.iconResId),
                    contentDescription = null,
                    tint = log.emotion.color,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${log.emotion.label} · ${log.emotionScore}",
                    style = AppTextStyles.Pretendard.Header4.copy(
                        color = log.emotion.color,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 큰 제목
        Text(
            text = log.title,
            modifier = Modifier.fillMaxWidth(),
            style = AppTextStyles.Pretendard.Header1.copy(
                color = AppColors.white,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 난이도 카드
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(AppColors.cardBackground, RoundedCornerShape(12.dp))
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "난이도",
                    style = AppTextStyles.Pretendard.Header3.copy(color = AppColors.white)
                )
                LevelIndicator(level = log.difficultyLevel)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 학습 내용 카드
        ContentCard(
            title = "학습 내용",
            content = log.learned,
            titleColor = AppColors.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 어려웠던 점 카드
        ContentCard(
            title = "어려웠던 점",
            content = log.difficult,
            titleColor = AppColors.orange
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 내일 할 일 카드
        if (log.tomorrowPlan.isNotBlank()) {
            ContentCard(
                title = "내일 할 일",
                content = log.tomorrowPlan,
                titleColor = AppColors.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        log.aiFeedBack?.let { feedback ->
            Spacer(modifier = Modifier.height(16.dp))

            // AI 피드백 카드
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppColors.cardBackground, RoundedCornerShape(12.dp))
                    .border(1.dp, AppColors.lightBlue30, RoundedCornerShape(12.dp))
                    .padding(20.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_ai),
                            contentDescription = null,
                            tint = AppColors.lightBlue,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "AI 피드백",
                            style = AppTextStyles.Pretendard.Header3.copy(
                                color = AppColors.white,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = feedback,
                        style = AppTextStyles.Pretendard.Body.copy(
                            color = AppColors.subTextColor,
                            fontSize = 15.sp,
                            lineHeight = 22.sp
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "created: ${DateUtils.formatDateTime(log.createdAt)}",
            style = AppTextStyles.Pretendard.Label.copy(
                color = AppColors.labelTextColor,
                fontSize = 12.sp
            )
        )
        
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailScreenPreview() {
    val dummyLog = Til(
        id = 1,
        createdAt = System.currentTimeMillis(),
        title = "CSS Flexbox 완벽 정리",
        learned = "justify-content와 align-items의 차이를 확실히 알게 됐다.",
        difficult = "flex-grow, flex-shrink, flex-basis를 함께 사용할 때 우선순위가 헷갈렸다.",
        emotionScore = 80,
        emotion = EmotionType.FULFILLMENT,
        difficultyLevel = 2,
        updatedAt = System.currentTimeMillis(),
        tomorrowPlan = "다음에는 CSS Grid에 대해서도 정리해보기로 했다.",
        aiFeedBack = "Flexbox는 레이아웃의 기본이에요! 잘하고 계세요!"
    )
    DetailScreen(
        state = DetailState(log = dummyLog)
    )
}