package me.ealanhill.wtfitnesschallenge

import java.util.*

fun getDaysInMonth(calendar: Calendar) : List<DateItem> {
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    val daysInMonth: Int = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    val dates: MutableList<DateItem> = ArrayList(daysInMonth)
    val month: String = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US)
    (1..daysInMonth).forEach {
        day -> dates.add(DateItem(month, day, 0))
    }
    return dates
}