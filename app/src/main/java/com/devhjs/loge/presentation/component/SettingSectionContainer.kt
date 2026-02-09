package com.devhjs.loge.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devhjs.loge.R
import com.devhjs.loge.presentation.designsystem.AppColors

@Composable
fun SettingSectionContainer(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(0.7.dp, AppColors.border, RoundedCornerShape(10.dp))
            .background(AppColors.cardBackground, RoundedCornerShape(10.dp))
    ) {
        content()
    }
}

@Preview
@Composable
private fun SettingSectionContainerPreview() {
    SettingSectionContainer {
        Column(modifier = Modifier.padding(16.dp)) {
            SettingActionItem(
                iconRes = R.drawable.ic_save,
                title = "데이터 내보내기",
                subtitle = "JSON 형식으로 저장",
                onClick = {}
            )
            Spacer(modifier = Modifier.height(16.dp))
            SettingActionItem(
                iconRes = R.drawable.ic_delete,
                title = "모든 데이터 삭제",
                subtitle = "복구할 수 없습니다",
                titleColor = AppColors.red,
                onClick = { }
            )
        }
    }
}
