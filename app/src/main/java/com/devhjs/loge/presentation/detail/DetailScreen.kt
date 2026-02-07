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
import com.devhjs.loge.domain.model.EmotionType
import com.devhjs.loge.presentation.component.ContentCard
import com.devhjs.loge.presentation.component.InformationChip
import com.devhjs.loge.presentation.component.LevelIndicator
import com.devhjs.loge.presentation.designsystem.AppColors
import com.devhjs.loge.presentation.designsystem.AppTextStyles
import com.devhjs.loge.presentation.util.color
import com.devhjs.loge.presentation.util.iconResId

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
) {
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
                text = "2026.02.04 (수)"
            )
            InformationChip(
                icon = R.drawable.ic_detail,
                text = "오전 04:45"
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 감정 섹션 (커스텀 대형 UI)
        val emotion = EmotionType.FULFILLMENT // TODO: 실제 데이터 반영
        val score = 80
        
        Box(
            modifier = Modifier
                .background(
                    color = emotion.color.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(32.dp)
                )
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = emotion.iconResId),
                    contentDescription = null,
                    tint = emotion.color,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${emotion.label} · $score",
                    style = AppTextStyles.Pretendard.Header4.copy(
                        color = emotion.color,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 큰 제목
        Text(
            text = "CSS Flexbox 완벽 정리",
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
                LevelIndicator(level = 2)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 학습 내용 카드
        ContentCard(
            title = "학습 내용",
            content = "justify-content와 align-items의 차이를 확실히 알게 됐다. flex-direction에 따라 main axis와 cross axis가 바뀌기 때문에 이를 이해하는 게 중요하다.",
            titleColor = AppColors.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 어려웠던 점 카드
        ContentCard(
            title = "어려웠던 점",
            content = "flex-grow, flex-shrink, flex-basis를 함께 사용할 때 우선순위가 헷갈렸다.",
            titleColor = AppColors.orange // AppColors에서 정의된 orange 사용
        )

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
                    text = "Flexbox는 레이아웃의 기본이에요! flex 속성의 단축 문법(flex: 1 1 0%)을 익히면 더 효율적으로 코딩할 수 있어요. 잘하고 계세요!",
                    style = AppTextStyles.Pretendard.Body.copy(
                        color = AppColors.subTextColor,
                        fontSize = 15.sp,
                        lineHeight = 22.sp
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "created: 2026-02-05 04:45",
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
    DetailScreen()
}