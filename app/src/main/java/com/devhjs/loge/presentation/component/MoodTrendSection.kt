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
import com.devhjs.loge.presentation.designsystem.AppColors
import com.devhjs.loge.presentation.designsystem.AppTextStyles
import com.patrykandpatrick.vico.compose.axis.axisLabelComponent
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.component.shape.DashedShape
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.ShapeComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.entry.entryModelOf

@Composable
fun MoodTrendSection() {
    // 차트 더미 데이터 설정
    // X축은 인덱스, Y축은 감정 점수
    val model = entryModelOf(50f, 72f, 60f, 70f, 92f, 58f)
    val xLabels = listOf("1/26", "1/27", "1/28", "1/29", "1/30", "1/31")

    // X축 라벨 포맷터 (인덱스를 날짜 텍스트로 변환)
    val horizontalAxisValueFormatter = AxisValueFormatter<AxisPosition.Horizontal.Bottom> { value, _ ->
        xLabels.getOrNull(value.toInt()) ?: ""
    }

    // Y축 라벨 포맷터 (정수형으로 표시)
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
            .height(300.dp)
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
                            lineThicknessDp = 3f, // 선 두께
                            lineBackgroundShader = null, // 하단 그라데이션 제거 (디자인에 따라 추가 가능)
                            point = ShapeComponent( // 데이터 포인트 (원형 점)
                                shape = Shapes.pillShape,
                                color = AppColors.iconPrimary.toArgb(),
                                strokeWidthDp = 0f,
                            ),
                            pointSizeDp = 10f, // 점 크기
                        )
                    )
                ),
                model = model,
                startAxis = rememberStartAxis(
                    valueFormatter = verticalAxisValueFormatter,
                    itemPlacer = AxisItemPlacer.Vertical.default(maxItemCount = 5),
                    guideline = guideline,
                    label = axisLabelComponent(color = AppColors.subTextColor)
                ),
                bottomAxis = rememberBottomAxis(
                    valueFormatter = horizontalAxisValueFormatter,
                    guideline = guideline,
                    label = axisLabelComponent(color = AppColors.subTextColor)
                ),
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}


@Preview
@Composable
private fun MoodTrendSectionPreview() {
    MoodTrendSection()
}