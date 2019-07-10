package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

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
    val isInPast = date.time >= this.time
    return when (val diff = (abs(date.time - this.time))) {
        in TimeUnits.SECOND.toMillis(0)..TimeUnits.SECOND.toMillis(1) -> "только что"
        in TimeUnits.SECOND.toMillis(0)..TimeUnits.SECOND.toMillis(45) -> if (isInPast) "несколько секунд назад"  else "через несколько секунд"
        in TimeUnits.SECOND.toMillis(45)..TimeUnits.SECOND.toMillis(75) -> if (isInPast) "минуту назад"  else "через минуту"
        in TimeUnits.SECOND.toMillis(75)..TimeUnits.MINUTE.toMillis(45) -> TimeUnits.MINUTE.getDeclensionRepresentation(diff, isInPast)
        in TimeUnits.MINUTE.toMillis(45)..TimeUnits.MINUTE.toMillis(75) -> if (isInPast) "час назад" else "через час"
        in TimeUnits.MINUTE.toMillis(75)..TimeUnits.HOUR.toMillis(22) -> TimeUnits.HOUR.getDeclensionRepresentation(diff, isInPast)
        in TimeUnits.HOUR.toMillis(22)..TimeUnits.HOUR.toMillis(26) -> if (isInPast) "день назад" else "через день"
        in TimeUnits.HOUR.toMillis(26)..TimeUnits.DAY.toMillis(360) -> TimeUnits.DAY.getDeclensionRepresentation(diff, isInPast)
        else -> if (isInPast) "более года назад" else "более чем через год"
    }
}

enum class TimeUnits(
    private val convertValue: Long,
    private val declensionValues: List<String>
) {
    SECOND(1000, listOf("секунду", "секунды", "секунд")),
    MINUTE(1000 * 60, listOf("минуту", "минуты", "минут")),
    HOUR(1000 * 60 * 60, listOf("час", "часа", "часов")),
    DAY(1000 * 60 * 60 * 24, listOf("день", "дня", "дней"));

    fun toMillis(value: Int) = convertValue * value
    fun getValueFromMillis(value: Long) = value / convertValue
    fun getDeclensionRepresentation(value: Long, isInPast: Boolean) =
        "${if (isInPast) "" else "через " }${getValueFromMillis(value)} ${getDeclensionStringValue(getValueFromMillis(value))}${if (isInPast) " назад" else ""}"

    private fun getDeclensionStringValue(value: Long): String {
        return when (val resultValue = abs(value) % 100) {
            1L -> declensionValues[0]
            in 2..4 -> declensionValues[1]
            0L, in 5..20 -> declensionValues[2]
            else -> getDeclensionStringValue(resultValue % 10)
        }
    }
}