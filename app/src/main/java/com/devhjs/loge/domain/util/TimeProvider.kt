package com.devhjs.loge.domain.util

import java.time.LocalDate
import java.time.LocalDateTime

interface TimeProvider {
    fun getCurrentDate(): LocalDate
    fun getCurrentDateTime(): LocalDateTime
    fun getCurrentTimeMillis(): Long
}
