package com.devhjs.loge.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.devhjs.loge.presentation.designsystem.AppColors

@Composable
fun SettingActionItem(
    iconRes: Int,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    titleColor: Color = AppColors.titleTextColor
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = if (titleColor == AppColors.red) AppColors.red else AppColors.white,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    color = titleColor,
                    fontSize = 14.sp
                )
                Text(
                    text = subtitle,
                    color = AppColors.labelTextColor,
                    fontSize = 12.sp
                )
            }
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_forward),
            contentDescription = null,
            tint = AppColors.labelTextColor,
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