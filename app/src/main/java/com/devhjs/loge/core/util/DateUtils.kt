package com.devhjs.loge.core.util

import com.devhjs.loge.domain.model.YearGridInfo
import java.time.Instant
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateUtils {

    /** 해당 월의 시작/종료 타임스탬프 반환 ("yyyy-MM" → Pair<시작, 종료>) */
    fun getMonthStartEndTimestamps(month: String): Pair<Long, Long> {
        val yearMonth = YearMonth.parse(month)
        val startOfMonth = yearMonth.atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val endOfMonth = yearMonth.atEndOfMonth().atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        return Pair(startOfMonth, endOfMonth)
    }

    /** 타임스탬프 → ISO 날짜 문자열 ("yyyy-MM-dd") */
    fun formatToIsoDate(timestamp: Long): String {
        return Instant.ofEpochMilli(timestamp)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .format(DateTimeFormatter.ISO_DATE)
    }

    /** 타임스탬프에서 해당 월의 일(day) 값 추출 (1~31) */
    fun getDayOfMonth(timestamp: Long): Int {
        return Instant.ofEpochMilli(timestamp)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .dayOfMonth
    }

    /** 타임스탬프 → 시간 문자열 ("오전 09:30") */
    fun formatToTime(timestamp: Long): String {
        return Instant.ofEpochMilli(timestamp)
            .atZone(ZoneId.systemDefault())
            .format(DateTimeFormatter.ofPattern("a hh:mm", java.util.Locale.KOREA))
    }

    /** 타임스탬프 → 날짜 문자열 ("yyyy.MM.dd") */
    fun formatToDate(timestamp: Long): String {
        return Instant.ofEpochMilli(timestamp)
            .atZone(ZoneId.systemDefault())
            .format(DateTimeFormatter.ofPattern("yyyy.MM.dd", java.util.Locale.KOREA))
    }

    /** 타임스탬프 → 요일 문자열 ("월", "화" 등) */
    fun formatToDayOfWeek(timestamp: Long): String {
        return Instant.ofEpochMilli(timestamp)
            .atZone(ZoneId.systemDefault())
            .format(DateTimeFormatter.ofPattern("E", java.util.Locale.KOREA))
    }

    /** 현재 월의 시작(1일 00:00:00)/종료(말일 23:59:59) 타임스탬프 반환 */
    fun getCurrentMonthStartEnd(): Pair<Long, Long> {
        val now = YearMonth.now()
        val startOfMonth = now.atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val endOfMonth = now.atEndOfMonth().atTime(23, 59, 59, 999_999_999).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        return Pair(startOfMonth, endOfMonth)
    }

    /** 오늘의 시작(00:00:00)/종료(23:59:59) 타임스탬프 반환 */
    fun getTodayStartEnd(): Pair<Long, Long> {
        val now = LocalDate.now()
        val startOfDay = now.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val endOfDay = now.atTime(23, 59, 59, 999_999_999).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        return Pair(startOfDay, endOfDay)
    }

    /** 오늘 날짜를 "yyyy.MM.dd" 형식으로 반환 */
    fun getTodayString(): String {
        return formatToDate(System.currentTimeMillis())
    }

    /** formatToDate의 별칭 */
    fun formatDate(timestamp: Long): String {
        return formatToDate(timestamp)
    }

    /** formatToTime의 별칭 */
    fun formatTime(timestamp: Long): String {
        return formatToTime(timestamp)
    }

    /** 타임스탬프 → 날짜+시간 문자열 ("yyyy-MM-dd HH:mm") */
    fun formatDateTime(timestamp: Long): String {
        return Instant.ofEpochMilli(timestamp)
            .atZone(ZoneId.systemDefault())
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", java.util.Locale.KOREA))
    }

    /**
     * 해당 연도의 시작/종료 타임스탬프 반환
     * @param year 연도 (예: 2026)
     */
    fun getYearStartEndTimestamps(year: Int): Pair<Long, Long> {
        val startOfYear = LocalDate.of(year, 1, 1)
            .atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val endOfYear = LocalDate.of(year, 12, 31)
            .atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        return Pair(startOfYear, endOfYear)
    }

    /**
     * 연간 활동 그래프(ContributionGraph)에 필요한 그리드 정보를 계산
     * @param year 연도 (예: 2026)
     */
    fun getYearGridInfo(year: Int): YearGridInfo {
        val janFirst = LocalDate.of(year, 1, 1)
        val decLast = LocalDate.of(year, 12, 31)

        // 1월 1일의 요일 오프셋 (월요일=0)
        val startOffset = janFirst.dayOfWeek.value - 1
        val totalDays = decLast.dayOfYear
        val totalWeeks = (totalDays + startOffset + 6) / 7

        // 월 라벨 위치 계산 (각 월이 시작하는 주 인덱스)
        val monthNames = listOf(
            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        )
        val monthLabels = (1..12).map { month ->
            val dayOfYear = LocalDate.of(year, month, 1).dayOfYear
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

    /**
     * 현재 주(일요일 ~ 토요일)의 시작/종료 타임스탬프 반환
     */
    /**
     * 특정 날짜가 속한 주(일요일 ~ 토요일)의 시작/종료 타임스탬프 반환
     */
    fun getWeekStartEnd(date: LocalDate): Pair<Long, Long> {
        val daysToSubtract = getDayIndex(date)
        
        val startOfWeek = date.minusDays(daysToSubtract.toLong())
        val endOfWeek = startOfWeek.plusDays(6)

        val startTimestamp = startOfWeek.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val endTimestamp = endOfWeek.atTime(23, 59, 59, 999_999_999).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        return Pair(startTimestamp, endTimestamp)
    }

    /**
     * 현재 주(일요일 ~ 토요일)의 시작/종료 타임스탬프 반환 (LocalDate.now() 사용)
     */
    fun getCurrentWeekStartEnd(): Pair<Long, Long> {
        return getWeekStartEnd(LocalDate.now())
    }

    /** 타임스탬프 → LocalDate 변환 */
    fun toLocalDate(timestamp: Long): LocalDate {
        return Instant.ofEpochMilli(timestamp)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }

    /**
     * 날짜의 요일 인덱스 반환 (일요일=0, 월요일=1, ..., 토요일=6)
     */
    fun getDayIndex(date: LocalDate): Int {
        val dayOfWeekValue = date.dayOfWeek.value
        return if (dayOfWeekValue == 7) 0 else dayOfWeekValue
    }
}
