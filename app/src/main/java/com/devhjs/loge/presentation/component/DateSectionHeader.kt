package com.devhjs.loge.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
 * 날짜 섹션 헤더 컴포넌트
 * 예: → 2026.02.05 (목) [today]
 */
@Composable
fun DateSectionHeader(
    date: String,
    dayOfWeek: String,
    isToday: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        // 화살표 아이콘
        Text(
            text = "→",
            style = AppTextStyles.Pretendard.Header4.copy(
                color = LogETheme.colors.primary
            )
        )
        
        Spacer(modifier = Modifier.width(8.dp))
        
        // 날짜 텍스트
        Text(
            text = "$date ($dayOfWeek)",
            style = AppTextStyles.Pretendard.Header4.copy(
                color = LogETheme.colors.titleTextColor
            )
        )
        
        // today 뱃지 오늘인 경우만 표시
        if (isToday) {
            Spacer(modifier = Modifier.width(12.dp))
            
            Box(
                modifier = Modifier
                    .background(
                        color = LogETheme.colors.primary,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "오늘",
                    style = AppTextStyles.Pretendard.Label.copy(
                        color = LogETheme.colors.black,
                        fontSize = 12.sp
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
private fun DateSectionHeaderPreview() {
    DateSectionHeader(
        date = "2026.02.05",
        dayOfWeek = "목",
        isToday = true
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
private fun DateSectionHeaderNotTodayPreview() {
    DateSectionHeader(
        date = "2026.02.04",
        dayOfWeek = "수",
        isToday = false
    )
}
