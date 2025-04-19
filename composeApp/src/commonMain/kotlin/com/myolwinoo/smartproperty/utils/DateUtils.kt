package com.myolwinoo.smartproperty.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

object DateUtils {

    fun toDisplayDate(milliseconds: Long): String {
        return Instant.fromEpochMilliseconds(milliseconds)
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .format(LocalDateTime.Format {
                year()
                char('-')
                monthNumber()
                char('-')
                dayOfMonth()
            })
    }

    // 2025-04-19T00:00:00.000000Z
    fun toDisplayDate(serverDate: String): String {
        return runCatching {
            Instant.parse(serverDate)
                .toLocalDateTime(TimeZone.currentSystemDefault())
                .format(LocalDateTime.Format {
                    monthName(MonthNames.ENGLISH_ABBREVIATED)
                    char(' ')
                    dayOfMonth()
                    chars(", ")
                    year()
                    chars(" at ")
                    hour(Padding.NONE)
                    char(':')
                    minute()
                    char(' ')
                    amPmMarker(am = "AM", pm = "PM")
                })
        }.getOrNull().orEmpty()
    }

    fun toServerDate(localDate: String): String {
        return runCatching {
            // 2025-03-17 13:00:00
            val localDateTime = LocalDateTime.parse(localDate, LocalDateTime.Format {
                year()
                char('-')
                monthNumber()
                char('-')
                dayOfMonth()
                char(' ')
                hour()
                char(':')
                minute()
                char(':')
                second()
            })

            val utcDateTime = localDateTime.toInstant(TimeZone.currentSystemDefault())
                .toLocalDateTime(TimeZone.UTC)

            utcDateTime.format(LocalDateTime.Format {
                year()
                char('-')
                monthNumber()
                char('-')
                dayOfMonth()
                char(' ')
                hour()
                char(':')
                minute()
                char(':')
                second()
            })
        }.getOrNull().orEmpty()
    }
}