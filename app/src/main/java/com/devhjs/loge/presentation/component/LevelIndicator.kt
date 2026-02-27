package com.devhjs.loge.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.loge.presentation.designsystem.LogETheme
import com.devhjs.loge.presentation.designsystem.AppTextStyles

/**
 * 난이도 레벨 표시 컴포넌트
 * 점 5개로 레벨 표시
 */
@Composable
fun LevelIndicator(
    text: String= "",
    modifier: Modifier = Modifier,
    level: Int = 0,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = text,
            style = AppTextStyles.Pretendard.Label.copy(
                color = LogETheme.colors.labelTextColor,
                fontSize = 12.sp
            )
        )

        Spacer(modifier = Modifier.width(4.dp))

        // 5개의 점 (레벨에 따라 색상 적용)
        repeat(5) { index ->
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        color = if (index < level) LogETheme.colors.primary else LogETheme.colors.labelTextColor.copy(
                            alpha = 0.3f
                        ),
                        shape = CircleShape
                    )
            )
        }
    }
}

@Preview
@Composable
private fun LevelIndicatorPreview() {
    LevelIndicator(level = 3)
}