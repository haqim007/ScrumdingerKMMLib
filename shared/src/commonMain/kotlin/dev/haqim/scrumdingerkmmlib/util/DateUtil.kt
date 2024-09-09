package dev.haqim.scrumdingerkmmlib.util

import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.offsetAt
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun getDateTimeNow(
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): LocalDateTime {
    // Get the current instant
    val instant: Instant = Clock.System.now()

    // Convert Instant to LocalDateTime in UTC
    val dateTime: LocalDateTime = instant.toLocalDateTime(timeZone)

    // Format with UTC offset
    return dateTime

}

fun getDateTimeNow(): LocalDateTime {
    // Get the current instant
    val instant: Instant = Clock.System.now()

    // Convert Instant to LocalDateTime in UTC
    val dateTime: LocalDateTime = instant.toLocalDateTime(TimeZone.UTC)

    // Format with UTC offset
    return dateTime

}

fun LocalDateTime.toString(
    pattern: String = "yyyy-MM-dd HH:mm:ss",
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): String{
    return pattern
        .replace("yyyy", this.year.toString().padStart(4, '0'))
        .replace("MM", this.monthNumber.toString().padStart(2, '0'))
        .replace("dd", this.dayOfMonth.toString().padStart(2, '0'))
        .replace("HH", this.hour.toString().padStart(2, '0'))
        .replace("mm", this.minute.toString().padStart(2, '0'))
        .replace("ss", this.second.toString().padStart(2, '0'))
        .replace("Z", formatTimeZoneOffset(timeZone)) // Assuming UTC offset
}

fun LocalDateTime.toEpochMillis(
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): Long {
    return this.toInstant(timeZone).toEpochMilliseconds()
}

fun LocalDateTime.toISO8601String(): String {
    return this.toString() // ISO-8601 formatted string
}

fun getDateTimeNow(
    pattern: String = "yyyy-MM-dd HH:mm:ss",
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): String {
    // Get the current instant
    val instant: Instant = Clock.System.now()

    // Convert Instant to LocalDateTime in UTC
    val dateTime: LocalDateTime = instant.toLocalDateTime(timeZone)

    // Format with UTC offset
    return pattern
        .replace("yyyy", dateTime.year.toString().padStart(4, '0'))
        .replace("MM", dateTime.monthNumber.toString().padStart(2, '0'))
        .replace("dd", dateTime.dayOfMonth.toString().padStart(2, '0'))
        .replace("HH", dateTime.hour.toString().padStart(2, '0'))
        .replace("mm", dateTime.minute.toString().padStart(2, '0'))
        .replace("ss", dateTime.second.toString().padStart(2, '0'))
        .replace("Z", formatTimeZoneOffset(timeZone)) // Assuming UTC offset

}

fun formatTimeZoneOffset(timeZone: TimeZone): String {
    val offset = timeZone.offsetAt(Clock.System.now()).totalSeconds
    val sign = if (offset >= 0) "+" else "-"
    val absOffset = if (offset < 0) -offset else offset
    val hours = (absOffset / 3600).toString().padStart(2, '0')
    val minutes = ((absOffset % 3600) / 60).toString().padStart(2, '0')
    return "$sign$hours$minutes"
}

fun iso8601StringToLocalDateTime(iso8601String: String): LocalDateTime {
    // Parse the ISO 8601 string to Instant
    val instant = Instant.parse(iso8601String)

    // Convert Instant to LocalDateTime in UTC
    return instant.toLocalDateTime(TimeZone.UTC)
}
