package com.devhjs.loge.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.loge.presentation.designsystem.LogETheme
import com.devhjs.loge.presentation.designsystem.AppTextStyles

@Composable
fun ContentCard(
    title: String,
    content: String,
    titleColor: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(LogETheme.colors.cardBackground, RoundedCornerShape(12.dp))
            .padding(20.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "//",
                    style = AppTextStyles.JetBrain.Header3.copy(
                        color = titleColor,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = AppTextStyles.Pretendard.Header3.copy(
                        color = LogETheme.colors.white,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = content,
                style = AppTextStyles.Pretendard.Body.copy(
                    color = LogETheme.colors.subTextColor,
                    fontSize = 15.sp,
                    lineHeight = 22.sp
                )
            )
        }
    }
}