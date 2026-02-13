package com.devhjs.loge.data.util

import com.devhjs.loge.domain.util.TimeProvider
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class RealTimeProvider @Inject constructor() : TimeProvider {
    override fun getCurrentDate(): LocalDate = LocalDate.now()
    override fun getCurrentDateTime(): LocalDateTime = LocalDateTime.now()
    override fun getCurrentTimeMillis(): Long = System.currentTimeMillis()
}
