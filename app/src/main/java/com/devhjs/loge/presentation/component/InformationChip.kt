package com.devhjs.loge.presentation.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.devhjs.loge.R
import com.devhjs.loge.presentation.designsystem.LogETheme
import com.devhjs.loge.presentation.designsystem.AppTextStyles

@Composable
fun InformationChip(
    modifier: Modifier = Modifier,
    icon: Int,
    text: String,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource(icon),
            tint = LogETheme.colors.iconPrimary,
            contentDescription = text,
        )
        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = text,
            style = AppTextStyles.Pretendard.Label.copy(
                color = LogETheme.colors.labelTextColor
            )
        )
        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun InformationChipPreview() {
    InformationChip(
        icon = R.drawable.ic_date,
        text = "2026.02.04(수)"
    )
}