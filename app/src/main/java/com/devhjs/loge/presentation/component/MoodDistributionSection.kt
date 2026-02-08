package com.devhjs.loge.presentation.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.loge.presentation.designsystem.AppColors
import com.devhjs.loge.presentation.designsystem.AppTextStyles
import kotlin.math.atan2

@Composable
fun MoodDistributionSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .background(AppColors.cardBackground, RoundedCornerShape(10.dp))
            .border(1.dp, AppColors.border, RoundedCornerShape(10.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "감정 분포",
                style = AppTextStyles.Pretendard.Header5.copy(color = AppColors.subTextColor)
            )
            Spacer(modifier = Modifier.height(24.dp))
            
            // 파이 차트 영역
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                var selectedIndex by remember { mutableIntStateOf(-1) }
                
                val slices = listOf(
                    Triple(0.4f, AppColors.blue, "성장"),
                    Triple(0.3f, Color(0xFF8B5CF6), "혼란"),
                    Triple(0.2f, AppColors.amber, "고군분투"),
                    Triple(0.1f, AppColors.red, "자부심")
                )

                Canvas(
                    modifier = Modifier
                        .size(180.dp)
                        .pointerInput(Unit) {
                            detectTapGestures { offset ->
                                val center = Offset(size.width / 2f, size.height / 2f)
                                val x = offset.x - center.x
                                val y = offset.y - center.y
                                
                                var angle = Math.toDegrees(atan2(y.toDouble(), x.toDouble())).toFloat()
                                if (angle < 0) angle += 360f
                                
                                angle = (angle + 90f) % 360f
                                
                                var currentAngle = 0f
                                var clickedIndex = -1
                                
                                for ((index, slice) in slices.withIndex()) {
                                    val sweepAngle = slice.first * 360f
                                    if (angle >= currentAngle && angle < currentAngle + sweepAngle) {
                                        clickedIndex = index
                                        break
                                    }
                                    currentAngle += sweepAngle
                                }
                                
                                selectedIndex = if (selectedIndex == clickedIndex) -1 else clickedIndex
                            }
                        }
                ) {
                    val defaultStrokeWidth = 30.dp.toPx()
                    val selectedStrokeWidth = 40.dp.toPx()
                    
                    val baseRadius = size.minDimension / 2f - selectedStrokeWidth / 2f
                    val center = Offset(size.width / 2f, size.height / 2f)
                    val borderStrokeWidth = 2.dp.toPx()
                    
                    var startAngle = -90f
                    
                    slices.forEachIndexed { index, (fraction, color, _) ->
                        val sweepAngle = fraction * 360f
                        val isSelected = index == selectedIndex
                        val currentWidth = if (isSelected) selectedStrokeWidth else defaultStrokeWidth
                        
                        val outerRadius = baseRadius + currentWidth / 2f
                        val innerRadius = baseRadius - currentWidth / 2f
                        
                        val path = Path().apply {
                            arcTo(
                                rect = Rect(
                                    left = center.x - outerRadius,
                                    top = center.y - outerRadius,
                                    right = center.x + outerRadius,
                                    bottom = center.y + outerRadius
                                ),
                                startAngleDegrees = startAngle,
                                sweepAngleDegrees = sweepAngle,
                                forceMoveTo = false
                            )
                            arcTo(
                                rect = Rect(
                                    left = center.x - innerRadius,
                                    top = center.y - innerRadius,
                                    right = center.x + innerRadius,
                                    bottom = center.y + innerRadius
                                ),
                                startAngleDegrees = startAngle + sweepAngle,
                                sweepAngleDegrees = -sweepAngle,
                                forceMoveTo = false
                            )
                            close()
                        }

                        drawPath(path = path, color = color, style = Fill)
                        drawPath(path = path, color = Color.White, style = Stroke(width = borderStrokeWidth))
                        
                        startAngle += sweepAngle
                    }
                }
                
                // 중앙 텍스트 표시
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (selectedIndex != -1) {
                        Text(
                            text = slices[selectedIndex].third,
                            style = AppTextStyles.Pretendard.Header4.copy(color = AppColors.white)
                        )
                        Text(
                            text = "${(slices[selectedIndex].first * 100).toInt()}%",
                            style = AppTextStyles.Pretendard.Label.copy(fontSize = 12.sp, color = AppColors.subTextColor)
                        )
                    } else {
                        Text(
                            text = "총 5개",
                            style = AppTextStyles.Pretendard.Header4.copy(color = AppColors.white)
                        )
                        Text(
                            text = "감정 기록",
                            style = AppTextStyles.Pretendard.Label.copy(fontSize = 12.sp, color = AppColors.subTextColor)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
               MoodLegendItem(color = AppColors.blue, label = "성장 (2)")
               MoodLegendItem(color = AppColors.amber, label = "고군분투 (1)")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                MoodLegendItem(color = Color(0xFF8B5CF6), label = "혼란 (2)")
                MoodLegendItem(color = AppColors.red, label = "자부심 (1)")
            }
        }
    }
}

@Preview
@Composable
private fun MoodDistributionSectionPreview() {
    MoodDistributionSection()
}