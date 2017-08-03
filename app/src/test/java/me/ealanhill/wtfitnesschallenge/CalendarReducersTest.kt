package me.ealanhill.wtfitnesschallenge

import junit.framework.Assert.assertTrue
import me.ealanhill.wtfitnesschallenge.action.InitializeCalendarAction
import me.ealanhill.wtfitnesschallenge.calendar.CalendarReducers
import me.ealanhill.wtfitnesschallenge.calendar.CalendarState
import me.ealanhill.wtfitnesschallenge.calendar.DateItem
import org.junit.Test
import java.util.*

class CalendarReducersTest {

    @Test
    fun get_days_in_month_should_return_the_correct_number_of_days_for_current_month() {
        val calendar: Calendar = Calendar.getInstance()
        val calendarState: CalendarState = CalendarState(Collections.emptyList(), calendar)
        val daysInMonth: List<DateItem> = CalendarReducers.initializeCalendar()
                .reduce(InitializeCalendarAction, calendarState)
                .dateItems

        val secondCalendar: Calendar = Calendar.getInstance()
        secondCalendar.set(Calendar.DAY_OF_MONTH, 1)

        assertTrue("Incorrect days, got " + daysInMonth.size + ", should be " + secondCalendar.getActualMaximum(Calendar.DAY_OF_MONTH),
                daysInMonth.size == secondCalendar.getActualMaximum(Calendar.DAY_OF_MONTH))
    }

    @Test
    fun get_days_in_month_should_return_the_correct_number_of_days_for_specified_month() {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, Calendar.FEBRUARY)
        calendar.set(Calendar.YEAR, 2017)
        val calendarState: CalendarState = CalendarState(Collections.emptyList(), calendar)
        val daysInMonth: List<DateItem> = CalendarReducers.initializeCalendar()
                .reduce(InitializeCalendarAction, calendarState)
                .dateItems

        assertTrue("Incorrect days, got " + daysInMonth.size + ", should be 28", daysInMonth.size == 28)
    }

    @Test
    fun get_days_in_month_should_return_the_correct_month_for_current_month() {
        val calendar: Calendar = Calendar.getInstance()
        val calendarState: CalendarState = CalendarState(Collections.emptyList(), calendar)
        val daysInMonth: List<DateItem> = CalendarReducers.initializeCalendar()
                .reduce(InitializeCalendarAction, calendarState)
                .dateItems

        val secondCalendar: Calendar = Calendar.getInstance()
        val month: String = secondCalendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US)

        assertTrue("Incorrect month, got " + daysInMonth[1].month + ", should be " + month, month.equals(daysInMonth[1].month))
    }

    @Test
    fun get_days_in_month_should_return_the_correct_month_for_specified_month() {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, Calendar.FEBRUARY)
        val calendarState: CalendarState = CalendarState(Collections.emptyList(), calendar)
        val daysInMonth: List<DateItem> = CalendarReducers.initializeCalendar()
                .reduce(InitializeCalendarAction, calendarState)
                .dateItems

        assertTrue("Incorrect month, got " + daysInMonth[1].month + ", should be Feb", "Feb".equals(daysInMonth[1].month))
    }
}