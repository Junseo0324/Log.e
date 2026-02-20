package com.devhjs.loge.presentation.component

import android.graphics.RectF
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
import com.patrykandpatrick.vico.core.chart.values.ChartValuesProvider
import com.patrykandpatrick.vico.core.component.marker.MarkerComponent
import com.patrykandpatrick.vico.core.component.shape.DashedShape
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.text.VerticalPosition
import com.patrykandpatrick.vico.core.context.DrawContext
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

            override fun draw(
                context: DrawContext,
                bounds: RectF,
                markedEntries: List<Marker.EntryModel>,
                chartValuesProvider: ChartValuesProvider,
            ) = with(context) {
                markedEntries.map { it.location.x }.toSet().forEach { x ->
                    guideline.drawVertical(context, bounds.top, bounds.bottom, x)
                }

                val halfIndicatorSize = indicatorSizeDp * density / 2f
                var entryX = 0f
                var entryY = 0f

                markedEntries.forEach { model ->
                    onApplyEntryColor?.invoke(model.color)
                    indicator.draw(
                        context,
                        model.location.x - halfIndicatorSize,
                        model.location.y - halfIndicatorSize,
                        model.location.x + halfIndicatorSize,
                        model.location.y + halfIndicatorSize,
                    )
                    entryX = model.location.x
                    entryY = model.location.y
                }

                val text = labelFormatter.getLabel(markedEntries, chartValuesProvider.getChartValues())
                val labelBounds = RectF()
                label.getTextBounds(context = context, text = text, width = bounds.width().toInt(), outRect = labelBounds)
                val halfOfTextWidth = labelBounds.width() / 2f
                
                val x = when {
                    entryX - halfOfTextWidth < bounds.left -> bounds.left + halfOfTextWidth
                    entryX + halfOfTextWidth > bounds.right -> bounds.right - halfOfTextWidth
                    else -> entryX
                }

                label.drawText(
                    context = context,
                    text = text,
                    textX = x,
                    textY = entryY - labelBounds.height() - (8f * density),
                    verticalPosition = VerticalPosition.Bottom,
                    maxTextWidth = minOf(bounds.right - x, x - bounds.left).toInt() * 2,
                )
            }

            override fun getInsets(
                context: MeasureContext,
                outInsets: Insets,
                horizontalDimensions: HorizontalDimensions,
            ) = Unit
        }
    }
}
