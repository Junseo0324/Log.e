package com.devhjs.loge.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.loge.R
import com.devhjs.loge.presentation.designsystem.LogETheme
import com.devhjs.loge.presentation.designsystem.AppTextStyles

@Composable
fun StatCard(
    icon: Int,
    label: String,
    value: String,
    valueSize: Int = 24,
    iconTint: Color = LogETheme.colors.white
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .background(LogETheme.colors.cardBackground, RoundedCornerShape(10.dp))
            .border(1.dp, LogETheme.colors.border, RoundedCornerShape(10.dp))
            .padding(16.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = label,
                    tint = iconTint,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = label,
                    style = AppTextStyles.Pretendard.Label.copy(color = LogETheme.colors.contentTextColor, fontSize = 12.sp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                style = AppTextStyles.Pretendard.Header1.copy(fontSize = valueSize.sp, color = LogETheme.colors.titleTextColor)
            )
        }
    }
}

@Preview
@Composable
private fun StatCardPreview() {
    StatCard(
        icon = R.drawable.ic_code,
        label = "commits",
        value = "6",
        iconTint = LogETheme.colors.primary
    )
}