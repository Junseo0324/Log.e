package com.devhjs.loge.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devhjs.loge.presentation.designsystem.AppColors
import com.devhjs.loge.presentation.designsystem.AppTextStyles

@Composable
fun ContributionGraphSection() {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppColors.cardBackground, RoundedCornerShape(10.dp))
                .border(1.dp, AppColors.border, RoundedCornerShape(10.dp))
                .padding(16.dp)
        ) {
            Column {
                // Title
                Text(
                    text = "활동 그래프",
                    style = AppTextStyles.JetBrain.Header3,
                    color = AppColors.titleTextColor
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    repeat(13) { colIndex ->
                         Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            repeat(7) { rowIndex -> 
                                val isFilled = colIndex >= 11 && rowIndex >= 0
                                
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .background(
                                            color = if (isFilled) AppColors.iconPrimary else Color.Transparent,
                                            shape = RoundedCornerShape(2.dp)
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = if (isFilled) Color.Transparent else AppColors.border,
                                            shape = RoundedCornerShape(2.dp)
                                        )
                                )
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Column {
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = AppColors.border
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("총 15개 로그", style = AppTextStyles.Pretendard.Label.copy(color = AppColors.contentTextColor))
                        Spacer(modifier = Modifier.width(16.dp))
                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(12.dp)
                                .background(AppColors.border)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text("11일 활동", style = AppTextStyles.Pretendard.Label.copy(color = AppColors.contentTextColor))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ContributionGraphSectionPreview() {
    ContributionGraphSection()
}