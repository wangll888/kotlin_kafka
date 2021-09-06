package com.example.kotlinkafkaproducer.Utils

import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

class DateUtils {
    companion object {
        fun toDate(localDateTime: LocalDateTime): Date {
            return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
        }

        fun toLocalDate(date: Date): LocalDate {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        }

        fun toLocalDate(date: java.sql.Date): LocalDate {
            return Date(date.time).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        }

        fun dateNow(): Date {
            return Date()
        }

        fun localDateTimeNow(): LocalDateTime {
            return LocalDateTime.now()
        }

        fun localDateNow(): LocalDate {
            return LocalDate.now()
        }

        fun nowInEpochSeconds(): Long {
            return inEpochSeconds(localDateTimeNow())
        }

        fun inEpochSeconds(date: LocalDateTime): Long {
            return date.atZone(ZoneId.systemDefault()).toEpochSecond()
        }

        fun epochSecondToLocalDateTime(epochSecond: Long): LocalDateTime {
            return LocalDateTime.ofInstant(Instant.ofEpochSecond(epochSecond), ZoneId.systemDefault())
        }

        fun format(date: LocalDate, pattern: String): String {
            return date.format(DateTimeFormatter.ofPattern(pattern))
        }

        fun format(date: LocalDateTime, pattern: String): String {
            return date.format(DateTimeFormatter.ofPattern(pattern))
        }

        fun format(date: Date, pattern: String): String {
            return SimpleDateFormat(pattern).format(date)
        }

        fun parseLocalDate(dateStr: String, pattern: String): LocalDate {
            val dayFormatter = DateTimeFormatter.ofPattern(pattern)
            return LocalDate.parse(dateStr, dayFormatter)
        }

        fun parseDate(dateStr: String, pattern: String): Date {
            val dayFormatter = SimpleDateFormat(pattern)
            return dayFormatter.parse(dateStr)
        }

        fun parseLocalDateTime(dateStr: String, pattern: String): LocalDateTime {
            val dayFormatter = DateTimeFormatter.ofPattern(pattern)
            return LocalDateTime.parse(dateStr, dayFormatter)
        }

        fun getGapDays(startDay: Date, endDay: Date): Long {
            return ChronoUnit.DAYS.between(toLocalDate(startDay), toLocalDate(endDay))
        }

        fun localDateToDate(localDate: LocalDate): Date {
            val zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault())
            return Date.from(zonedDateTime.toInstant())
        }

        fun getAddDay(date:Date,day:Int):Date{
            val calendar=GregorianCalendar()
            calendar.time=date
            calendar.add(Calendar.DATE,day)
            return calendar.time
        }

        fun differToDay(date: Date, dateTo: Date): Int {
            return ((dateTo.time - date.time) / (1000 * 3600 * 24)).toInt()
        }
    }
}
