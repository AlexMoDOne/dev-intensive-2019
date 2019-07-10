package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time
    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val interval: Long = (date.time - this.time)
    val msec = if (interval > 0) interval else -interval
    val future = interval < msec

    if (msec <= 1000L) return "только что"

    val sec: Long = msec / 1000L
    if (sec <= 45) return "${if (future) "через " else ""}несколько секунд${if (future) "" else " назад"}"
    if (sec <= 75) return "${if (future) "через " else ""}минуту${if (future) "" else " назад"}"

    val min: Long = sec / 60
    if (min <= 45L) return "${if (future) "через " else ""}${TimeUnits.MINUTE.plural(min.toInt())}${if (future) "" else " назад"}"
    if (min <= 75L) return "${if (future) "через " else ""}час${if (future) "" else " назад"}"

    val hour: Long = min / 60
    if (hour <= 22L) return "${if (future) "через " else ""}${TimeUnits.HOUR.plural(hour.toInt())}${if (future) "" else " назад"}"
    if (hour <= 26L) return "${if (future) "через " else ""}день${if (future) "" else " назад"}"

    val day: Long = hour / 24
    if (day <= 360L) return "${if (future) "через " else ""}${TimeUnits.DAY.plural(day.toInt())}${if (future) "" else " назад"}"

    return when (future) {
        true -> "более чем через год"
        false -> "более года назад"
    }
}

enum class TimeUnits {
    SECOND {
        override fun plural(num: Int): String =
            "$num ${if (num % 10 == 1) "секунду" else if (num % 10 in 2..4) "секунды" else "секунд"}"
    },
    MINUTE {
        override fun plural(num: Int): String =
            "$num ${if (num % 10 == 1) "минуту" else if (num % 10 in 2..4) "минуты" else "минут"}"
    },
    HOUR {
        override fun plural(num: Int): String =
            "$num ${if (num % 10 == 1) "час" else if (num % 10 in 2..4) "часа" else "часов"}"
    },
    DAY {
        override fun plural(num: Int): String =
            "$num ${if (num % 10 == 1) "день" else if (num % 10 in 2..4) "дня" else "дней"}"
    };

    abstract fun plural(num: Int): String
}