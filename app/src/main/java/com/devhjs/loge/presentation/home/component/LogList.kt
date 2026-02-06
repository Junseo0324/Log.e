package com.devhjs.loge.presentation.home.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.devhjs.loge.presentation.component.DateSectionHeader
import com.devhjs.loge.presentation.home.LogItem

/**
 * 로그 리스트 컴포넌트
 * 날짜별로 그룹화하여 섹션 헤더와 로그 카드를 표시
 */
@Composable
fun LogList(
    logs: List<LogItem>,
    onLogClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    // 날짜별로 그룹화
    val groupedLogs = logs.groupBy { it.date }

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        groupedLogs.forEach { (date, logsForDate) ->
            // 날짜 섹션 헤더
            item(key = "header_$date") {
                DateSectionHeader(
                    date = date,
                    dayOfWeek = logsForDate.firstOrNull()?.dayOfWeek ?: "",
                    isToday = logsForDate.firstOrNull()?.isToday ?: false
                )
            }

            // 해당 날짜의 로그 카드들
            items(
                items = logsForDate,
                key = { it.id }
            ) { logItem ->
                LogItemCard(
                    item = logItem,
                    onClick = { onLogClick(logItem.id) }
                )
            }
        }
    }
}