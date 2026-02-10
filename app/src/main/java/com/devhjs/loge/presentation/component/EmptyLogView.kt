package com.devhjs.loge.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devhjs.loge.R
import com.devhjs.loge.presentation.designsystem.AppColors
import com.devhjs.loge.presentation.designsystem.AppTextStyles

@Composable
fun EmptyLogView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .width(56.dp)
                .height(56.dp)
                .background(
                    color = AppColors.cardBackground,
                    shape = RoundedCornerShape(12.dp)
                )
                .border(1.dp, color = AppColors.border,RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_home_filled),
                tint = AppColors.border,
                contentDescription = "empty icon"
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "// 로그가 없습니다",
            style = AppTextStyles.JetBrain.Body.copy(
                color = AppColors.white,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "개발 여정을 기록해보세요",
            style = AppTextStyles.Pretendard.Body.copy(
                color = AppColors.labelTextColor
            )
        )
    }
}
@Preview
@Composable
private fun EmptyLogViewPreview() {
    EmptyLogView()
}