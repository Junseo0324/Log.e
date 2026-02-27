package com.devhjs.loge.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.loge.R
import com.devhjs.loge.presentation.designsystem.LogETheme
import com.devhjs.loge.presentation.designsystem.AppTextStyles

@Composable
fun SettingToggleItem(
    iconRes: Int,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean = true,
    isReadOnly: Boolean = false,
    checkedColor: Color = LogETheme.colors.primary
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = LogETheme.colors.white,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    style = AppTextStyles.JetBrain.Label.copy(color = LogETheme.colors.titleTextColor, fontSize = 14.sp),
                )
                Text(
                    text = subtitle,
                    style = AppTextStyles.JetBrain.Label.copy(color = LogETheme.colors.labelTextColor, fontSize = 14.sp),
                )
            }
        }

        Switch(
            checked = checked,
            onCheckedChange = if (isReadOnly) null else onCheckedChange,
            enabled = enabled,
            colors = SwitchDefaults.colors(
                checkedThumbColor = LogETheme.colors.white,
                checkedTrackColor = checkedColor,
                uncheckedThumbColor = LogETheme.colors.white,
                uncheckedTrackColor = Color(0xFF404040),
                disabledCheckedTrackColor = checkedColor.copy(alpha = 0.5f),
                disabledUncheckedTrackColor = Color(0xFF404040).copy(alpha = 0.5f)
            ),
            modifier = Modifier.scale(0.8f)
        )
    }
}

@Preview
@Composable
private fun SettingToggleItemPreview() {
    Column {
        SettingToggleItem(
            iconRes = R.drawable.ic_time,
            title = "알림",
            subtitle = "학습 리마인더 받기",
            checked = true,
            onCheckedChange = { }
        )
        Spacer(modifier = Modifier.height(30.dp))
        SettingToggleItem(
            iconRes = R.drawable.ic_time,
            title = "알림",
            subtitle = "학습 리마인더 받기",
            checked = false,
            onCheckedChange = { }
        )
    }
}