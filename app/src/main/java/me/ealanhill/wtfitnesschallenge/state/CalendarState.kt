package me.ealanhill.wtfitnesschallenge.state

import me.ealanhill.wtfitnesschallenge.DateItem
import java.util.Collections
import java.util.Calendar

data class CalendarState(val dateItems: MutableList<DateItem> = Collections.emptyList(),
                         val calendar: Calendar = Calendar.getInstance())