package com.devhjs.loge.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.loge.domain.model.ChartPoint
import com.devhjs.loge.presentation.designsystem.AppColors
import com.devhjs.loge.presentation.designsystem.AppTextStyles
import com.patrykandpatrick.vico.compose.axis.axisLabelComponent
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.scroll.rememberChartScrollSpec
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatrick.vico.core.component.shape.DashedShape
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.entry.entryOf

@Composable
fun MoodTrendSection(
    emotionScoreList: List<ChartPoint>
) {
    // 데이터가 없으면 빈 상태 표시
    if (emotionScoreList.isEmpty()) {
        EmptyChartSection(title = "감정 점수 추이")
        return
    }

    // ChartPoint를 Vico Entry로 변환
    val entries = emotionScoreList.mapIndexed { index, point ->
        entryOf(index, point.y)
    }
    val model = entryModelOf(entries)

    // X축 라벨: 일(day) 표시
    val xLabels = emotionScoreList.map { "${it.x.toInt()}일" }

    val horizontalAxisValueFormatter = AxisValueFormatter<AxisPosition.Horizontal.Bottom> { value, _ ->
        xLabels[value.toInt()] ?: ""
    }

    // 데이터를 약 5개 구간으로 나누어 라벨이 5개 정도만 보이도록 간격 설정
    val labelSpacing = if (emotionScoreList.size > 5) {
        (emotionScoreList.size / 4).coerceAtLeast(1)
    } else {
        1
    }

    val verticalAxisValueFormatter = AxisValueFormatter<AxisPosition.Vertical.Start> { value, _ ->
        value.toInt().toString()
    }

    // 그리드 라인 설정 (점선)
    val dashedShape = DashedShape(
        shape = Shapes.rectShape,
        dashLengthDp = 5f,
        gapLengthDp = 5f
    )
    val guideline = LineComponent(
        color = AppColors.border.toArgb(),
        thicknessDp = 1f,
        shape = dashedShape
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.cardBackground, RoundedCornerShape(10.dp))
            .border(1.dp, AppColors.border, RoundedCornerShape(10.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "감정 점수 추이",
                style = AppTextStyles.Pretendard.Header5.copy(color = AppColors.subTextColor)
            )

            Spacer(modifier = Modifier.height(16.dp))
            
            Chart(
                chart = lineChart(
                    lines = listOf(
                        LineChart.LineSpec(
                            lineColor = AppColors.iconPrimary.toArgb(),
                            lineThicknessDp = 3f,
                            lineBackgroundShader = null,
                            point = null,
                        )
                    ),
                    axisValuesOverrider = AxisValuesOverrider.fixed(
                        minY = 0f, maxY = 100f,
                        minX = -0.5f, maxX = emotionScoreList.size - 0.5f
                    )
                ),
                marker = rememberMarker(),
                model = model,
                startAxis = rememberStartAxis(
                    valueFormatter = verticalAxisValueFormatter,
                    itemPlacer = AxisItemPlacer.Vertical.default(maxItemCount = 5),
                    guideline = guideline,
                    label = axisLabelComponent(color = AppColors.subTextColor, textSize = 10.sp)
                ),
                bottomAxis = rememberBottomAxis(
                    valueFormatter = horizontalAxisValueFormatter,
                    itemPlacer = AxisItemPlacer.Horizontal.default(
                        spacing = labelSpacing,
                        addExtremeLabelPadding = true,
                        shiftExtremeTicks = true
                    ),
                    guideline = null,
                    label = axisLabelComponent(color = AppColors.subTextColor)
                ),
                chartScrollSpec = rememberChartScrollSpec(isScrollEnabled = false),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
    }
}


@Preview
@Composable
private fun MoodTrendSectionPreview() {
    MoodTrendSection(
        emotionScoreList = listOf(
            ChartPoint(1f, 50f),
            ChartPoint(24f, 72f),
            ChartPoint(31f, 60f),
            ChartPoint(24f, 70f),
            ChartPoint(25f, 92f),
            ChartPoint(26f, 58f),
            ChartPoint(19f, 50f),
            ChartPoint(2f, 72f),
            ChartPoint(3f, 60f),
            ChartPoint(4f, 70f),
            ChartPoint(5f, 92f),
            ChartPoint(6f, 58f),
            ChartPoint(1f, 50f),
            ChartPoint(2f, 72f),
            ChartPoint(3f, 60f),
            ChartPoint(4f, 70f),
            ChartPoint(5f, 92f),
            ChartPoint(6f, 58f),

        )
    )
}