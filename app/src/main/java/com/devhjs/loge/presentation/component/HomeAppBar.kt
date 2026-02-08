package com.devhjs.loge.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devhjs.loge.R
import com.devhjs.loge.presentation.designsystem.AppColors
import com.devhjs.loge.presentation.designsystem.AppTextStyles

/**
 * 홈 화면 상단 앱바 컴포넌트
 * 상태 호이스팅을 적용하여 외부에서 데이터와 콜백을 주입받음
 */
@Composable
fun HomeAppBar(
    currentDate: String,
    logCount: Int,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(AppColors.cardBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Icon(
                        painter = painterResource(R.drawable.ic_home_filled),
                        tint = AppColors.iconPrimary,
                        contentDescription = "Home Icon"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Log.e",
                        style = AppTextStyles.JetBrain.Header2.copy(color = AppColors.white)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                CustomButton(
                    modifier = Modifier.width(70.dp),
                    backgroundColor = AppColors.primary,
                    icon = R.drawable.ic_add,
                    contentDescription = "Add Icon",
                    text = "추가",
                    contentColor = AppColors.black,
                    onClick = onAddClick
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = currentDate,
                    style = AppTextStyles.Pretendard.Label.copy(color = AppColors.homeLabelTextColor)
                )
                Spacer(modifier = Modifier.width(8.dp))
                VerticalDivider(
                    modifier = Modifier.height(20.dp),
                    thickness = 1.dp,
                    color = AppColors.homeLabelTextColor
                )
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "$logCount logs",
                    style = AppTextStyles.Pretendard.Label.copy(color = AppColors.homeLabelTextColor)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeAppBarPreview() {
    HomeAppBar(
        currentDate = "2026.02.05",
        logCount = 15,
        onAddClick = {}
    )
}