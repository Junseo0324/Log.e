package com.devhjs.loge.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.HorizontalDivider
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
import com.devhjs.loge.presentation.designsystem.AppColors
import com.devhjs.loge.presentation.designsystem.AppTextStyles

@Composable
fun AiAnalysisResultCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.cardBackground, RoundedCornerShape(10.dp))
            .border(1.dp, AppColors.border, RoundedCornerShape(10.dp))
            .padding(16.dp)
    ) {
        Column {
            // Header 영역
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_ai),
                    contentDescription = null,
                    tint = AppColors.iconPrimary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "분석 결과",
                    style = AppTextStyles.Pretendard.Header4.copy(color = AppColors.titleTextColor, fontWeight = FontWeight.Bold),
                    color = AppColors.titleTextColor
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 감정
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "감정:",
                    style = AppTextStyles.Pretendard.Body.copy(color = AppColors.subTextColor)
                )
                EmotionTag(
                    emotion = EmotionType.FRUSTRATION,
                    score = 75
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 난이도
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "난이도:",
                    style = AppTextStyles.Pretendard.Body.copy(color = AppColors.subTextColor)
                )
                LevelIndicator(level = 3)
            }

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider(color = AppColors.border, thickness = 1.dp)

            Spacer(modifier = Modifier.height(16.dp))

            // AI 피드백
            Text(
                text = "// AI 피드백",
                style = AppTextStyles.Pretendard.Body.copy(color = AppColors.subTextColor, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "오늘도 열심히 학습하셨네요! 배운 내용을 실제 프로젝트에 적용해보면서 더 깊이 이해할 수 있을 거예요. 어려운 부분이 있어도 포기하지 마시고, 하나씩 해결해나가다 보면 분명 성장할 수 있습니다. 화이팅!",
                style = AppTextStyles.Pretendard.Body.copy(color = AppColors.subTextColor, lineHeight = 20.sp)
            )
        }
    }
}

@Preview
@Composable
private fun AiAnalysisResultCardPreview() {
    AiAnalysisResultCard()
}