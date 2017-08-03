package me.ealanhill.wtfitnesschallenge.calendar

import java.util.*

data class CalendarState(val dateItems: List<DateItem> = Collections.emptyList(),
                         val calendar: Calendar = Calendar.getInstance()) {

    fun getDate(date: Int): DateItem {
        dateItems.forEach { dateItem: DateItem ->
            if (dateItem.date == date) {
                return dateItem
            }
        }
        throw IllegalArgumentException("Incorrect date for the month, date = " + date)
    }
}