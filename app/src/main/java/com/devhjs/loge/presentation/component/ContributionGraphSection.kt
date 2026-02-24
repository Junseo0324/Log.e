package com.devhjs.loge.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.loge.core.util.DateUtils
import com.devhjs.loge.presentation.designsystem.AppColors
import com.devhjs.loge.presentation.designsystem.AppTextStyles
import java.time.LocalDate

@Composable
fun ContributionGraphSection(
    yearlyLearnedDates: List<String>,
    totalLogs: Int,
    selectedMonth: String // "yyyy-MM" 형식
) {
    // 선택된 월의 연도 추출
    val year = selectedMonth.take(4).toInt()

    // 학습한 날짜 Set으로 변환
    val learnedDateSet = remember(yearlyLearnedDates) { yearlyLearnedDates.toSet() }
    val activeDays = yearlyLearnedDates.size

    // DateUtils에서 그리드 정보 계산
    val gridInfo = remember(year) { DateUtils.getYearGridInfo(year) }

    // UI 재구성을 방지하기 위한 모든 날짜 상태 1년치 사전 계산 (캐싱)
    val gridCells = remember(year, gridInfo, learnedDateSet) {
        val baseDate = LocalDate.of(year, 1, 1)
        List(gridInfo.totalWeeks) { weekIndex ->
            List(7) { dayOfWeek ->
                val dayIndex = weekIndex * 7 + dayOfWeek - gridInfo.startOffset + 1
                val isValidDay = dayIndex in 1..gridInfo.totalDays
                val isFilled = if (isValidDay) {
                    val date = baseDate.plusDays((dayIndex - 1).toLong())
                    learnedDateSet.contains(date.toString())
                } else {
                    false
                }
                ContributionCellState(isValidDay = isValidDay, isFilled = isFilled)
            }
        }
    }

    // 요일 라벨
    val dayLabels = listOf("월", "화", "수", "목", "금", "토", "일")

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppColors.cardBackground, RoundedCornerShape(10.dp))
                .border(1.dp, AppColors.border, RoundedCornerShape(10.dp))
                .padding(16.dp)
        ) {
            Column {
                // 타이틀
                Text(
                    text = "${year}년 활동 그래프",
                    style = AppTextStyles.JetBrain.Header3,
                    color = AppColors.titleTextColor
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 가로 스크롤 영역 (요일 라벨 + 그리드)
                Row {
                    // 요일 라벨 (고정)
                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                        modifier = Modifier.padding(top = 18.dp) // 월 라벨 높이만큼 오프셋
                    ) {
                        dayLabels.forEach { label ->
                            Box(
                                modifier = Modifier
                                    .size(width = 20.dp, height = 12.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Text(
                                    text = label,
                                    style = AppTextStyles.Pretendard.Label.copy(
                                        fontSize = 9.sp,
                                        color = AppColors.subTextColor
                                    )
                                )
                            }
                        }
                    }

                    // 스크롤 가능한 그리드
                    val scrollState = rememberScrollState()
                    Column(
                        modifier = Modifier
                            .horizontalScroll(scrollState)
                            .weight(1f)
                    ) {
                        // 월 라벨 행
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            var labelIndex = 0
                            repeat(gridInfo.totalWeeks) { weekIndex ->
                                val showLabel = labelIndex < gridInfo.monthLabels.size &&
                                        gridInfo.monthLabels[labelIndex].first == weekIndex
                                Box(
                                    modifier = Modifier.size(width = 12.dp, height = 14.dp),
                                    contentAlignment = Alignment.BottomStart
                                ) {
                                    if (showLabel) {
                                        Text(
                                            text = gridInfo.monthLabels[labelIndex].second,
                                            style = AppTextStyles.Pretendard.Label.copy(
                                                fontSize = 7.sp,
                                                color = AppColors.subTextColor
                                            ),
                                            textAlign = TextAlign.Start,
                                            softWrap = false,
                                            maxLines = 1
                                        )
                                        labelIndex++
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        // 잔디 그리드 (7행 × N열)
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            gridCells.forEach { weekColumn ->
                                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                    weekColumn.forEach { cell ->
                                        Box(
                                            modifier = Modifier
                                                .size(12.dp)
                                                .background(
                                                    color = when {
                                                        !cell.isValidDay -> Color.Transparent
                                                        cell.isFilled -> AppColors.iconPrimary
                                                        else -> AppColors.darkBlue
                                                    },
                                                    shape = RoundedCornerShape(2.dp)
                                                )
                                                .then(
                                                    if (cell.isValidDay && !cell.isFilled) {
                                                        Modifier.border(
                                                            width = 0.5.dp,
                                                            color = AppColors.border2,
                                                            shape = RoundedCornerShape(2.dp)
                                                        )
                                                    } else Modifier
                                                )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 하단 요약 정보
                Column {
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = AppColors.border
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "총 ${totalLogs}개 로그",
                            style = AppTextStyles.Pretendard.Label.copy(color = AppColors.contentTextColor)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(12.dp)
                                .background(AppColors.border)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            "${activeDays}일 활동",
                            style = AppTextStyles.Pretendard.Label.copy(color = AppColors.contentTextColor)
                        )
                    }
                }
            }
        }
    }
}

private data class ContributionCellState(
    val isValidDay: Boolean,
    val isFilled: Boolean
)

@Preview
@Composable
private fun ContributionGraphSectionPreview() {
    ContributionGraphSection(
        yearlyLearnedDates = listOf(
            "2026-01-05", "2026-01-10", "2026-01-15",
            "2026-02-01", "2026-02-03", "2026-02-05", "2026-02-10",
            "2026-03-01", "2026-03-15", "2026-03-20",
            "2026-04-05", "2026-04-10", "2026-04-25",
            "2026-06-12", "2026-07-04", "2026-08-15",
            "2026-10-03", "2026-12-25"
        ),
        totalLogs = 18,
        selectedMonth = "2026-02"
    )
}