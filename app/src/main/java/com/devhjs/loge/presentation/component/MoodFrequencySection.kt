package com.devhjs.loge.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.devhjs.loge.domain.model.EmotionType
import com.devhjs.loge.presentation.designsystem.AppColors
import com.devhjs.loge.presentation.designsystem.AppTextStyles
import com.patrykandpatrick.vico.compose.axis.axisLabelComponent
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.chart.column.ColumnChart
import com.patrykandpatrick.vico.core.component.shape.DashedShape
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.entry.entryOf
import com.devhjs.loge.presentation.util.color

@Composable
fun MoodFrequencySection(
    emotionDistribution: Map<EmotionType, Int>
) {
    // 데이터가 없으면 빈 상태 표시
    if (emotionDistribution.isEmpty()) {
        EmptyChartSection(title = "감정별 빈도")
        return
    }

    // 모든 EmotionType에 대해 데이터 준비 (없는 감정은 0으로)
    val allEmotions = EmotionType.entries
    val entries = allEmotions.mapIndexed { index, emotion ->
        listOf(entryOf(index, emotionDistribution[emotion] ?: 0))
    }
    val model = entryModelOf(*entries.toTypedArray())

    val xLabels = allEmotions.map { it.label }

    // 감정별 색상 매핑
    // EmotionExtensions 확장 프로퍼티로 색상 매핑
    val columns = allEmotions.map { emotion ->
        LineComponent(
            color = emotion.color.toArgb(),
            thicknessDp = 20f,
            shape = Shapes.roundedCornerShape(topLeftPercent = 20, topRightPercent = 20)
        )
    }

    // 축 설정
    val horizontalAxisValueFormatter = AxisValueFormatter<AxisPosition.Horizontal.Bottom> { value, _ ->
        xLabels.getOrNull(value.toInt()) ?: ""
    }

    val verticalAxisValueFormatter = AxisValueFormatter<AxisPosition.Vertical.Start> { value, _ ->
        value.toInt().toString()
    }

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
            .height(300.dp)
            .background(AppColors.cardBackground, RoundedCornerShape(10.dp))
            .border(1.dp, AppColors.border, RoundedCornerShape(10.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "감정별 빈도",
                style = AppTextStyles.Pretendard.Header5.copy(color = AppColors.subTextColor)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Chart(
                chart = columnChart(
                    columns = columns,
                    mergeMode = ColumnChart.MergeMode.Stack
                ),
                model = model,
                startAxis = rememberStartAxis(
                    valueFormatter = verticalAxisValueFormatter,
                    guideline = guideline,
                    label = axisLabelComponent(color = AppColors.subTextColor)
                ),
                bottomAxis = rememberBottomAxis(
                    valueFormatter = horizontalAxisValueFormatter,
                    guideline = null,
                    label = axisLabelComponent(color = AppColors.subTextColor)
                ),
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
private fun MoodFrequencySectionPreview() {
    MoodFrequencySection(
        emotionDistribution = mapOf(
            EmotionType.FULFILLMENT to 3,
            EmotionType.SATISFACTION to 1,
            EmotionType.NORMAL to 1,
            EmotionType.DIFFICULTY to 1,
            EmotionType.FRUSTRATION to 2
        )
    )
}