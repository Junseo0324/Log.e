package com.devhjs.loge.presentation.component

import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.loge.presentation.designsystem.LogETheme
import com.devhjs.loge.presentation.designsystem.AppTextStyles


/**
 * 프로필 정보 행 (읽기 전용)
 */
@Composable
fun ProfileInfoRow(
    label: String,
    value: String,
    iconRes: Int
) {
    Column {
        Text(
            text = label,
            style = AppTextStyles.Pretendard.Body.copy(
                color = LogETheme.colors.labelTextColor,
                fontSize = 12.sp,
                lineHeight = 16.sp
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = LogETheme.colors.background,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = LogETheme.colors.subTextColor,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = value,
                style = AppTextStyles.Pretendard.Body.copy(
                    color = LogETheme.colors.white,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            )
        }
    }
}