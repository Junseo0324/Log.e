package com.devhjs.loge.presentation.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.devhjs.loge.MainActivity
import com.devhjs.loge.R

@Composable
fun ActivityWidgetContent(
    activeDayCount: Int // 최근 14일 활동 일수
) {
    val activeColor = Color(0xFF00BC7D)
    val inactiveColor = Color(0xFF252525)

    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(Color(0xFF0D0D0D))
            .cornerRadius(10.dp)
            .padding(17.dp)
            .clickable(actionStartActivity<MainActivity>())
    ) {
        // 상단 헤더
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = GlanceModifier.fillMaxWidth()
        ) {
            Image(
                provider = ImageProvider(R.drawable.ic_widget_activity),
                contentDescription = null,
                modifier = GlanceModifier.size(16.dp)
            )
            Spacer(modifier = GlanceModifier.width(8.dp))
            Text(
                text = "ACTIVITY",
                style = TextStyle(
                    color = ColorProvider(Color(0xFFA1A1A1)),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }

        Spacer(modifier = GlanceModifier.height(8.dp))

        Text(
            text = "최근 14일 활동",
            style = TextStyle(
                color = ColorProvider(Color(0xFF737373)),
                fontSize = 12.sp
            )
        )

        Spacer(modifier = GlanceModifier.defaultWeight())

        // 14개 활동 상태 박스 (7x2)
        Column(
            modifier = GlanceModifier.fillMaxWidth()
        ) {
            repeat(2) { rowIndex ->
                Row(
                    modifier = GlanceModifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(7) { colIndex ->
                        val index = rowIndex * 7 + colIndex
                        val isActive = index < activeDayCount
                        Box(
                            modifier = GlanceModifier
                                .defaultWeight()
                                .height(20.dp)
                                .padding(4.dp) // 간격 늘림
                                .cornerRadius(4.dp)
                                .background(if (isActive) activeColor else inactiveColor)
                        ) {}
                    }
                }
                
                // 두 줄 사이에 간격 추가
                if (rowIndex == 0) {
                     Spacer(modifier = GlanceModifier.height(4.dp))
                }
            }
        }
    }
}
