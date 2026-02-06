package com.devhjs.loge.core.util

import java.time.Instant
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateUtils {

    fun getMonthStartEndTimestamps(month: String): Pair<Long, Long> {
        val yearMonth = YearMonth.parse(month)
        val startOfMonth = yearMonth.atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val endOfMonth = yearMonth.atEndOfMonth().atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        return Pair(startOfMonth, endOfMonth)
    }

    fun formatToIsoDate(timestamp: Long): String {
        return Instant.ofEpochMilli(timestamp)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .format(DateTimeFormatter.ISO_DATE)
    }

    fun getDayOfMonth(timestamp: Long): Int {
        return Instant.ofEpochMilli(timestamp)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .dayOfMonth
    }
}
