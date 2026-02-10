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

    fun getTodayStartEnd(): Pair<Long, Long> {
        val now = java.time.LocalDate.now()
        val startOfDay = now.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val endOfDay = now.atTime(23, 59, 59, 999_999_999).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        return Pair(startOfDay, endOfDay)
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

    /**
     * 해당 월의 총 일 수 반환
     * @param month "yyyy-MM" 형식
     */
    fun getDaysInMonth(month: String): Int {
        return YearMonth.parse(month).lengthOfMonth()
    }

    /**
     * 해당 월 1일의 요일 반환 (월요일=0, 일요일=6)
     * @param month "yyyy-MM" 형식
     */
    fun getStartDayOfWeek(month: String): Int {
        return YearMonth.parse(month).atDay(1).dayOfWeek.value - 1
    }

    /**
     * 해당 연도의 시작/종료 타임스탬프 반환
     * @param year 연도 (예: 2026)
     */
    fun getYearStartEndTimestamps(year: Int): Pair<Long, Long> {
        val startOfYear = java.time.LocalDate.of(year, 1, 1)
            .atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val endOfYear = java.time.LocalDate.of(year, 12, 31)
            .atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        return Pair(startOfYear, endOfYear)
    }

    /**
     * 연간 활동 그래프(ContributionGraph)에 필요한 그리드 정보를 계산
     * @param year 연도 (예: 2026)
     */
    fun getYearGridInfo(year: Int): YearGridInfo {
        val janFirst = java.time.LocalDate.of(year, 1, 1)
        val decLast = java.time.LocalDate.of(year, 12, 31)

        // 1월 1일의 요일 오프셋 (월요일=0)
        val startOffset = janFirst.dayOfWeek.value - 1
        val totalDays = decLast.dayOfYear
        val totalWeeks = (totalDays + startOffset + 6) / 7

        // 월 라벨 위치 계산 (각 월이 시작하는 주 인덱스)
        val monthNames = listOf(
            "1월", "2월", "3월", "4월", "5월", "6월",
            "7월", "8월", "9월", "10월", "11월", "12월"
        )
        val monthLabels = (1..12).map { month ->
            val dayOfYear = java.time.LocalDate.of(year, month, 1).dayOfYear
            val weekIndex = (dayOfYear - 1 + startOffset) / 7
            Pair(weekIndex, monthNames[month - 1])
        }

        return YearGridInfo(
            startOffset = startOffset,
            totalDays = totalDays,
            totalWeeks = totalWeeks,
            monthLabels = monthLabels
        )
    }
}

/**
 * 연간 활동 그래프 그리드 정보
 * @param startOffset 1월 1일의 요일 오프셋 (월요일=0, 일요일=6)
 * @param totalDays 해당 연도의 총 일 수 (365 또는 366)
 * @param totalWeeks 그리드에 필요한 총 주(열) 수
 * @param monthLabels 각 월 라벨과 해당 주 인덱스
 */
data class YearGridInfo(
    val startOffset: Int,
    val totalDays: Int,
    val totalWeeks: Int,
    val monthLabels: List<Pair<Int, String>>
)
