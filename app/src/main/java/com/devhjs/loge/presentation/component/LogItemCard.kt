package com.devhjs.loge.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.loge.core.util.DateUtils
import com.devhjs.loge.domain.model.EmotionType
import com.devhjs.loge.domain.model.Til
import com.devhjs.loge.presentation.designsystem.AppColors
import com.devhjs.loge.presentation.designsystem.AppTextStyles

/**
 * 로그 아이템 카드 컴포넌트
 * 감정 태그 + 점수, 시간, 제목, 내용 미리보기, 난이도 레벨 표시
 */
@Composable
fun LogItemCard(
    item: Til,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val time = DateUtils.formatToTime(item.createdAt)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(AppColors.cardBackground)
            .border(1.dp, AppColors.border, RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Column {
            // 상단: 감정 태그 + 시간 + 화살표
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 감정 태그 (예: 🙂 기쁨 · 88)
                EmotionTag(
                    emotion = item.emotion,
                    score = item.emotionScore
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                // 시간
                Text(
                    text = time,
                    style = AppTextStyles.Pretendard.Label.copy(
                        color = AppColors.labelTextColor,
                        fontSize = 14.sp
                    )
                )
                
                Spacer(modifier = Modifier.weight(1f))
                
                // 화살표
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "상세 보기",
                    tint = AppColors.labelTextColor
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // 제목
            Text(
                text = item.title,
                style = AppTextStyles.Pretendard.Header3.copy(
                    color = AppColors.titleTextColor
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // 내용 미리보기
            Text(
                text = item.learned,
                style = AppTextStyles.Pretendard.Body.copy(
                    color = AppColors.contentTextColor,
                    fontSize = 14.sp
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // 난이도 레벨 표시 (점 5개)
            LevelIndicator(level = item.difficultyLevel)
        }
    }
}



@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
private fun LogItemCardPreview() {
    LogItemCard(
        item = Til(
            id = 1,
            createdAt = System.currentTimeMillis(),
            title = "Next.js App Router 마이그레이션",
            learned = "Pages Router에서 App Router로 전환하면서 Server Component와 Client Component의 차이를 이해했다.",
            difficult = "",
            emotionScore = 95,
            emotion = EmotionType.FULFILLMENT,
            difficultyLevel = 4,
            updatedAt = System.currentTimeMillis()
        ),
        onClick = {}
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
private fun LogItemCardConfusedPreview() {
    LogItemCard(
        item = Til(
            id = 2,
            createdAt = System.currentTimeMillis(),
            title = "Docker 컨테이너 이해하기",
            learned = "Docker로 개발 환경을 격리할 수 있다는 점이 인상 깊었다.",
            difficult = "",
            emotionScore = 45,
            emotion = EmotionType.DIFFICULTY,
            difficultyLevel = 4,
            updatedAt = System.currentTimeMillis()
        ),
        onClick = {}
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
private fun LogItemCardStrugglePreview() {
    LogItemCard(
        item = Til(
            id = 3,
            createdAt = System.currentTimeMillis(),
            title = "비동기 처리와 Promise",
            learned = "비동기 처리의 개념을 이해하고 Promise와 async/await 문법을 학습했다.",
            difficult = "",
            emotionScore = 15,
            emotion = EmotionType.FRUSTRATION,
            difficultyLevel = 5,
            updatedAt = System.currentTimeMillis()
        ),
        onClick = {}
    )
}
