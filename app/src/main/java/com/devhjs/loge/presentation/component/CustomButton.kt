package com.devhjs.loge.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devhjs.loge.R
import com.devhjs.loge.presentation.designsystem.AppColors
import com.devhjs.loge.presentation.designsystem.AppTextStyles

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color = AppColors.iconPrimary,
    icon: Int = R.drawable.ic_home_filled,
    contentDescription: String = "",
    text: String = "",
    contentColor: Color = AppColors.white,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth().height(32.dp)
            .background(color = backgroundColor, shape = RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                onClick()
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource(icon),
            tint = contentColor,
            contentDescription = contentDescription
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = AppTextStyles.Pretendard.Header5.copy(color = contentColor)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomButtonPreview() {
    CustomButton(
        modifier = Modifier.width(70.dp),
        text = "추가",
        icon = R.drawable.ic_add)
}