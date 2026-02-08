package com.devhjs.loge.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.loge.presentation.designsystem.AppColors
import com.devhjs.loge.presentation.designsystem.AppTextStyles
import com.patrykandpatrick.vico.compose.axis.axisLabelComponent
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.component.shape.shader.toDynamicShader
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.component.shape.DashedShape
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.entry.entryOf

@Composable
fun ScoreDifficultySection() {
    // 데이터 설정 (2개의 시리즈: 점수, 난이도)
    // 점수
    val scoreEntries = listOf(
        entryOf(0, 70), entryOf(1, 80), entryOf(2, 65), entryOf(3, 90), entryOf(4, 75)
    )
    // 난이도
    val difficultyEntries = listOf(
        entryOf(0, 40), entryOf(1, 50), entryOf(2, 45), entryOf(3, 60), entryOf(4, 55)
    )

    val model = entryModelOf(scoreEntries, difficultyEntries)

    val xLabels = listOf("2/1", "2/2", "2/3", "2/4", "2/5")

    val scoreColor = AppColors.primary
    val difficultyColor = Color(0xFF8E51FF)

    // 차트 라인 설정
    val lines = listOf(
        LineChart.LineSpec(
            lineColor = scoreColor.toArgb(),
            lineThicknessDp = 3f,
            point = null,
            lineBackgroundShader = Brush.verticalGradient(
                colors = listOf(
                    scoreColor.copy(alpha = 0.4f),
                    scoreColor.copy(alpha = 0.1f)
                )
            ).toDynamicShader()
        ),
        LineChart.LineSpec(
            lineColor = difficultyColor.toArgb(),
            lineThicknessDp = 3f,
            point = null,
            lineBackgroundShader = Brush.verticalGradient(
                colors = listOf(
                    difficultyColor.copy(alpha = 0.4f),
                    difficultyColor.copy(alpha = 0.1f)
                )
            ).toDynamicShader()
        )
    )

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
                text = "점수 & 난이도",
                style = AppTextStyles.Pretendard.Header5.copy(color = AppColors.subTextColor)
            )
            
            Spacer(modifier = Modifier.height(16.dp))

            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)) {
                Chart(
                    chart = lineChart(lines = lines),
                    model = model,
                    startAxis = rememberStartAxis(
                        valueFormatter = verticalAxisValueFormatter,
                        guideline = guideline,
                        label = axisLabelComponent(color = AppColors.subTextColor),
                        itemPlacer = AxisItemPlacer.Vertical.default(maxItemCount = 5)
                    ),
                    bottomAxis = rememberBottomAxis(
                        valueFormatter = horizontalAxisValueFormatter,
                        guideline = null,
                        label = axisLabelComponent(color = AppColors.subTextColor)
                    ),
                    modifier = Modifier.fillMaxSize()
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(scoreColor, RoundedCornerShape(4.dp))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "점수",
                        style = AppTextStyles.Pretendard.Label.copy(color = AppColors.subTextColor, fontSize = 12.sp)
                    )
                }
                
                Spacer(modifier = Modifier.width(24.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(difficultyColor, RoundedCornerShape(4.dp))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "난이도",
                        style = AppTextStyles.Pretendard.Label.copy(color = AppColors.subTextColor, fontSize = 12.sp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ScoreDifficultySectionPreview() {
    ScoreDifficultySection()
}
