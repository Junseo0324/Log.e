package com.devhjs.loge.presentation.stat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devhjs.loge.R
import com.devhjs.loge.presentation.component.ContributionGraphSection
import com.devhjs.loge.presentation.component.LogETopBar
import com.devhjs.loge.presentation.component.MoodDistributionSection
import com.devhjs.loge.presentation.component.MoodFrequencySection
import com.devhjs.loge.presentation.component.MoodTrendSection
import com.devhjs.loge.presentation.component.StatSummarySection
import com.devhjs.loge.presentation.designsystem.AppColors
import com.devhjs.loge.presentation.designsystem.AppTextStyles
import java.time.YearMonth
import java.time.format.DateTimeFormatter


@Composable
fun StatScreen(
    modifier: Modifier = Modifier
) {
    var yearMonth by remember { mutableStateOf(YearMonth.now()) }

    Scaffold(
        topBar = {
            LogETopBar(
                title = "통계 분석",
                titleIcon = R.drawable.ic_stat_filled,
                bottomContent = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { yearMonth = yearMonth.minusMonths(1) }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_back),
                                contentDescription = "Previous Month",
                                tint = AppColors.contentTextColor
                            )
                        }
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = yearMonth.format(DateTimeFormatter.ofPattern("yyyy.MM")),
                                style = AppTextStyles.Pretendard.Header3.copy(color = AppColors.titleTextColor)
                            )
                        }
                        IconButton(onClick = { yearMonth = yearMonth.plusMonths(1) }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_forward),
                                contentDescription = "Next Month",
                                tint = AppColors.contentTextColor,
                            )
                        }
                    }
                }
            )
        },
        containerColor = AppColors.background,
        modifier = modifier
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item { ContributionGraphSection() }
            item { StatSummarySection() }
            item { MoodTrendSection() }
            item { MoodDistributionSection() }
            item { MoodFrequencySection() }
        }
    }
}

@Preview
@Composable
fun StatScreenPreview() {
    StatScreen()
}