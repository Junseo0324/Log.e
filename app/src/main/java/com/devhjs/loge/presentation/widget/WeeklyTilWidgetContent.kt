package com.devhjs.loge.presentation.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
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
fun WeeklyTilWidgetContent(
    count: Int
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
                text = "$count",
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

        Spacer(modifier = GlanceModifier.defaultWeight())

        Row(
            modifier = GlanceModifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {
            repeat(7) { index ->
                val isActive = index < count
                val color = if (isActive) Color(0xFF00BC7D) else Color(0xFF353535)
                
                Box(
                    modifier = GlanceModifier
                        .defaultWeight()
                        .height(6.dp)
                        .then(
                            if (index < 6) GlanceModifier.padding(end = 2.dp) else GlanceModifier
                        )
                        .cornerRadius(3.dp)
                        .background(color)
                ) { }
            }
        }
    }
}

@Preview
@Composable
fun WeeklyTilWidgetContentPreview() {
    WeeklyTilWidgetContent(count = 5)
}
