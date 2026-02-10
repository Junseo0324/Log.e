package com.devhjs.loge.presentation.stat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devhjs.loge.presentation.component.ContributionGraphSection
import com.devhjs.loge.presentation.component.MoodDistributionSection
import com.devhjs.loge.presentation.component.MoodFrequencySection
import com.devhjs.loge.presentation.component.MoodTrendSection
import com.devhjs.loge.presentation.component.ScoreDifficultySection
import com.devhjs.loge.presentation.component.StatSummarySection

@Composable
fun StatScreen(
    state: StatState,
    modifier: Modifier = Modifier
) {
    val stat = state.stat

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            ContributionGraphSection(
                learnedDates = stat?.learnedDates ?: emptyList(),
                totalLogs = stat?.totalTil ?: 0,
                selectedMonth = state.selectedMonth
            )
        }
        item {
            StatSummarySection(
                totalTil = stat?.totalTil ?: 0,
                avgEmotion = stat?.avgEmotion ?: 0f,
                avgScore = stat?.avgScore ?: 0f,
                avgDifficulty = stat?.avgDifficulty ?: 0f
            )
        }
        item {
            MoodTrendSection(
                emotionScoreList = stat?.emotionScoreList ?: emptyList()
            )
        }
        item {
            MoodDistributionSection(
                emotionDistribution = state.emotionDistribution,
                totalLogs = stat?.totalTil ?: 0
            )
        }
        item {
            MoodFrequencySection(
                emotionDistribution = state.emotionDistribution
            )
        }
        item {
            ScoreDifficultySection(
                emotionScoreList = stat?.emotionScoreList ?: emptyList(),
                difficultyChartPoints = state.difficultyChartPoints
            )
        }
    }
}

@Preview
@Composable
fun StatScreenPreview() {
    StatScreen(state = StatState())
}