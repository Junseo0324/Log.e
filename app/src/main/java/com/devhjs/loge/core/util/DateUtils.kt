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

    fun formatToTime(timestamp: Long): String {
        return Instant.ofEpochMilli(timestamp)
            .atZone(ZoneId.systemDefault())
            .format(DateTimeFormatter.ofPattern("a hh:mm", java.util.Locale.KOREA))
    }

    fun formatToDate(timestamp: Long): String {
        return Instant.ofEpochMilli(timestamp)
            .atZone(ZoneId.systemDefault())
            .format(DateTimeFormatter.ofPattern("yyyy.MM.dd", java.util.Locale.KOREA))
    }

    fun formatToDayOfWeek(timestamp: Long): String {
        return Instant.ofEpochMilli(timestamp)
            .atZone(ZoneId.systemDefault())
            .format(DateTimeFormatter.ofPattern("E", java.util.Locale.KOREA))
    }

    fun getCurrentMonthStartEnd(): Pair<Long, Long> {
        val now = YearMonth.now()
        val startOfMonth = now.atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val endOfMonth = now.atEndOfMonth().atTime(23, 59, 59, 999_999_999).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        return Pair(startOfMonth, endOfMonth)
    }

    fun getTodayString(): String {
        return formatToDate(System.currentTimeMillis())
    }

    fun formatDate(timestamp: Long): String {
        return formatToDate(timestamp)
    }

    fun formatTime(timestamp: Long): String {
        return formatToTime(timestamp)
    }

    fun formatDateTime(timestamp: Long): String {
        return Instant.ofEpochMilli(timestamp)
            .atZone(ZoneId.systemDefault())
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", java.util.Locale.KOREA))
    }
}
