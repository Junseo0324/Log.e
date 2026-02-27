package com.devhjs.loge.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.loge.presentation.designsystem.LogETheme
import com.devhjs.loge.presentation.designsystem.AppTextStyles

@Composable
fun SummaryCard(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    iconColor: Color,
    title: String,
    mainValue: String,
    subValue: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .background(LogETheme.colors.background, RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = title,
                style = AppTextStyles.Pretendard.Label,
                color = Color.Gray,
                fontSize = 11.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = mainValue,
                style = AppTextStyles.Pretendard.Header2,
                color = LogETheme.colors.white,
                fontSize = 20.sp
            )
            if (subValue.isNotEmpty()) {
                Text(
                    text = " $subValue",
                    style = AppTextStyles.Pretendard.Label,
                    color = LogETheme.colors.labelTextColor,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(bottom = 3.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
        ) {
            content()
        }
    }
}