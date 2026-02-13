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
import androidx.glance.layout.fillMaxHeight
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.devhjs.loge.MainActivity
import com.devhjs.loge.R
import com.devhjs.loge.domain.model.WeeklyStats

@Composable
fun WeeklyTilWidgetContent(
    weeklyStats: WeeklyStats?
) {
    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(Color(0xFF0D0D0D))
            .cornerRadius(10.dp)
            .padding(16.dp)
            .clickable(actionStartActivity<MainActivity>())
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = GlanceModifier.fillMaxWidth()
        ) {
            Image(
                provider = ImageProvider(R.drawable.ic_date),
                contentDescription = null,
                modifier = GlanceModifier.size(16.dp)
            )
            Spacer(modifier = GlanceModifier.width(8.dp))
            Text(
                text = "WEEKLY",
                style = TextStyle(
                    color = ColorProvider(Color(0xFFA1A1A1)),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }

        Spacer(modifier = GlanceModifier.height(12.dp))

        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = GlanceModifier.fillMaxWidth()
        ) {
            Text(
                text = "${weeklyStats?.totalCount ?: 0}",
                style = TextStyle(
                    color = ColorProvider(Color.White),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = GlanceModifier.width(4.dp))
            Text(
                text = "개",
                style = TextStyle(
                    color = ColorProvider(Color(0xFF737373)),
                    fontSize = 14.sp
                ),
                modifier = GlanceModifier.padding(bottom = 6.dp)
            )
        }
        
        Spacer(modifier = GlanceModifier.height(4.dp))
        
        Text(
            text = "이번 주 TIL",
            style = TextStyle(
                color = ColorProvider(Color(0xFF737373)),
                fontSize = 12.sp
            )
        )

        Spacer(modifier = GlanceModifier.fillMaxHeight())

        Row(
            modifier = GlanceModifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {
            val activity = weeklyStats?.dailyActivity ?: List(7) { false }
            
            activity.forEachIndexed { index, isActive ->
                val color = if (isActive) Color(0xFF00BC7D) else Color(0xFF252525)
                
                Box(
                    modifier = GlanceModifier
                        .height(6.dp)
                        .fillMaxWidth()
                        .cornerRadius(3.dp)
                        .background(color)
                ) { }
                
                if (index < 6) {
                    Spacer(modifier = GlanceModifier.width(4.dp))
                }
            }
        }
    }
}
