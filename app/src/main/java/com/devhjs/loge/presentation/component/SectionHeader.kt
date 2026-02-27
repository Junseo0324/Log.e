package com.devhjs.loge.presentation.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.loge.R
import com.devhjs.loge.presentation.designsystem.LogETheme
import com.devhjs.loge.presentation.designsystem.AppTextStyles

@Composable
fun SectionHeader(iconRes: Int, title: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = LogETheme.colors.white,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            style = AppTextStyles.JetBrain.Label.copy(color = LogETheme.colors.subTextColor, fontSize = 14.sp),
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Preview
@Composable
private fun SectionHeaderPreview() {
    SectionHeader(iconRes = R.drawable.setting_outlined, title = "앱 설정")
}