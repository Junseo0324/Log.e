package com.devhjs.loge.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devhjs.loge.R
import com.devhjs.loge.presentation.designsystem.AppColors

@Composable
fun StatSummarySection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatCard(
                icon = R.drawable.ic_home_filled,
                label = "commits",
                value = "6",
                iconTint = AppColors.primary
            )
            StatCard(
                icon = R.drawable.ic_growth,
                label = "avg score",
                value = "67",
                iconTint = AppColors.orange
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatCard(
                icon = R.drawable.ic_heart,
                label = "mood",
                value = "성장",
                valueSize = 20,
                iconTint = AppColors.pink
            )
            StatCard(
                icon = R.drawable.ic_difficulty,
                label = "difficulty",
                value = "3.8",
                iconTint = AppColors.red
            )
        }
    }
}


@Preview
@Composable
private fun StatSummarySectionPreview() {
    StatSummarySection()
}