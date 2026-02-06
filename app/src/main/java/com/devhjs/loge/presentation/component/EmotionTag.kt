package com.devhjs.loge.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.loge.domain.model.EmotionType
import com.devhjs.loge.presentation.designsystem.AppTextStyles
import com.devhjs.loge.presentation.home.color
import com.devhjs.loge.presentation.home.iconResId

/**
 * 감정 태그 컴포넌트
 * 예: [Icon] 기쁨 · 88
 */
@Composable
fun EmotionTag(
    emotion: EmotionType,
    score: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = emotion.color.copy(alpha = 0.15f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 감정 아이콘
            Icon(
                painter = painterResource(id = emotion.iconResId),
                contentDescription = emotion.label,
                tint = emotion.color,
                modifier = Modifier.size(16.dp)
            )
            
            Spacer(modifier = Modifier.width(4.dp))
            
            // 감정 라벨 + 점수
            Text(
                text = "${emotion.label} · $score",
                style = AppTextStyles.Pretendard.Label.copy(
                    color = emotion.color,
                    fontSize = 12.sp
                )
            )
        }
    }
}

@Preview
@Composable
private fun EmotionTagPreview() {
    Column {
        EmotionTag(
            emotion = EmotionType.FULFILLMENT,
            score = 95,
        )
        EmotionTag(
            emotion = EmotionType.SATISFACTION,
            score = 95,
        )
        EmotionTag(
            emotion = EmotionType.NORMAL,
            score = 95,
        )
        EmotionTag(
            emotion = EmotionType.DIFFICULTY,
            score = 95,
        )
        EmotionTag(
            emotion = EmotionType.FRUSTRATION,
            score = 95,
        )
    }
}
