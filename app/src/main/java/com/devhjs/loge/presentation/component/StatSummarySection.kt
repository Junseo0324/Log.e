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
import com.devhjs.loge.domain.model.EmotionType
import com.devhjs.loge.presentation.designsystem.AppColors

@Composable
fun StatSummarySection(
    totalTil: Int,
    avgEmotion: Float,
    avgScore: Float,
    avgDifficulty: Float
) {
    // 평균 감정 점수로부터 대표 감정 라벨 결정
    val dominantEmotionLabel = EmotionType.fromScore(avgEmotion.toInt()).label

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
                value = totalTil.toString(),
                iconTint = AppColors.primary
            )
            StatCard(
                icon = R.drawable.ic_growth,
                label = "avg score",
                value = "%.0f".format(avgScore),
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
                value = dominantEmotionLabel,
                valueSize = 20,
                iconTint = AppColors.pink
            )
            StatCard(
                icon = R.drawable.ic_difficulty,
                label = "difficulty",
                value = "%.1f".format(avgDifficulty),
                iconTint = AppColors.red
            )
        }
    }
}

@Preview
@Composable
private fun StatSummarySectionPreview() {
    StatSummarySection(
        totalTil = 6,
        avgEmotion = 67f,
        avgScore = 67f,
        avgDifficulty = 3.8f
    )
}