package com.devhjs.loge.presentation.component

import android.graphics.Typeface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.devhjs.loge.presentation.designsystem.AppColors
import com.patrykandpatrick.vico.compose.component.overlayingComponent
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.core.chart.dimensions.HorizontalDimensions
import com.patrykandpatrick.vico.core.chart.insets.Insets
import com.patrykandpatrick.vico.core.component.marker.MarkerComponent
import com.patrykandpatrick.vico.core.component.shape.DashedShape
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.context.MeasureContext
import com.patrykandpatrick.vico.core.marker.Marker
import com.patrykandpatrick.vico.core.marker.MarkerLabelFormatter


private val DefaultMarkerLabelFormatter = MarkerLabelFormatter { markedEntries, _ ->
    val y = markedEntries.firstOrNull()?.entry?.y?.toInt() ?: 0
    "score : $y"
}

@Composable
internal fun rememberMarker(
    labelFormatter: MarkerLabelFormatter = DefaultMarkerLabelFormatter
): Marker {
    val labelBackground = shapeComponent(
        shape = Shapes.roundedCornerShape(allPercent = 25),
        color = AppColors.background,
        strokeColor = AppColors.white.copy(alpha = 0.5f),
        strokeWidth = 1.dp
    )

    val label = textComponent(
        background = labelBackground,
        lineCount = 5,
        padding = dimensionsOf(start = 12.dp, top = 8.dp, end = 12.dp, bottom = 8.dp),
        typeface = Typeface.MONOSPACE,
        color = AppColors.white,
    )

    val indicatorInner = shapeComponent(
        shape = Shapes.pillShape,
        color = AppColors.iconPrimary
    )

    val indicatorCenter = shapeComponent(
        shape = Shapes.pillShape,
        color = AppColors.white
    )

    val indicator = overlayingComponent(
        outer = indicatorCenter,
        inner = indicatorInner,
        innerPaddingAll = 2.dp
    )

    val guideline = LineComponent(
        color = Color.White.copy(alpha = 0.5f).toArgb(),
        thicknessDp = 2f,
        shape = DashedShape(
            shape = Shapes.pillShape,
            dashLengthDp = 10f,
            gapLengthDp = 5f
        )
    )

    return remember(label, indicator, guideline, labelFormatter) {
        object : MarkerComponent(label, indicator, guideline) {
            init {
                indicatorSizeDp = 12f
                onApplyEntryColor = { entryColor ->
                    with(indicatorCenter) {
                        color = Color.White.toArgb()
                    }
                    with(indicatorInner) {
                        color = entryColor
                    }
                }
                this.labelFormatter = labelFormatter
            }
            override fun getInsets(
                context: MeasureContext,
                outInsets: Insets,
                horizontalDimensions: HorizontalDimensions,
            ) {
                outInsets.top = label.getHeight(context)
            }
        }
    }
}
