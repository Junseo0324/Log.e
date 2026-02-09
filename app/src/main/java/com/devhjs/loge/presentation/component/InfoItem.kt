package com.devhjs.loge.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.loge.presentation.designsystem.AppColors

@Composable
fun InfoItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.background, RoundedCornerShape(10.dp))
            .padding(horizontal = 12.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = AppColors.labelTextColor,
            fontSize = 14.sp
        )
        Text(
            text = value,
            color = AppColors.titleTextColor,
            fontSize = 14.sp
        )
    }
}

@Preview
@Composable
private fun InfoItemPreview() {
    InfoItem(label = "버전", value = "v1.0.0")
}