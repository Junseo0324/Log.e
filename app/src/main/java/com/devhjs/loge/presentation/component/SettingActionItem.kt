package com.devhjs.loge.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
fun SettingActionItem(
    iconRes: Int,
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit,
    titleColor: Color = LogETheme.colors.titleTextColor,
    containerColor: Color = Color.Transparent
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(containerColor, RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .padding(if (containerColor != Color.Transparent) 12.dp else 0.dp)
            .height(if (containerColor != Color.Transparent) 44.dp else 60.dp), // 높이 조정
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = if (titleColor == LogETheme.colors.red) LogETheme.colors.red else LogETheme.colors.white,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    style = AppTextStyles.JetBrain.Label.copy(color = titleColor, fontSize = 14.sp),
                )
                if (subtitle != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = subtitle,
                        style = AppTextStyles.JetBrain.Label.copy(color = LogETheme.colors.labelTextColor, fontSize = 12.sp),
                    )
                }
            }
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_forward),
            contentDescription = null,
            tint = LogETheme.colors.labelTextColor,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Preview
@Composable
private fun SettingActionItemPreview() {
    SettingActionItem(
        iconRes = R.drawable.ic_save,
        title = "데이터 내보내기",
        subtitle = "JSON 형식으로 저장",
        onClick = {}
    )
}