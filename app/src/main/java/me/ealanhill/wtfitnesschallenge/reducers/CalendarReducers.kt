package me.ealanhill.wtfitnesschallenge.reducers

import java.util.ArrayList
import java.util.Calendar
import java.util.Locale

import me.ealanhill.wtfitnesschallenge.DateItem
import me.ealanhill.wtfitnesschallenge.action.Action
import me.ealanhill.wtfitnesschallenge.action.InitializeCalendarAction
import me.ealanhill.wtfitnesschallenge.state.CalendarState
import me.tatarka.redux.Reducer
import me.tatarka.redux.Reducers

object CalendarReducers {

    fun reducer(): Reducer<Action, CalendarState> {
        return Reducers.matchClass<Action, CalendarState>()
                .`when`(InitializeCalendarAction::class.java, initializeCalendar())
    }

    fun initializeCalendar(): Reducer<InitializeCalendarAction, CalendarState> {
        return Reducer { _, state ->
            val calendar = state.calendar
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            val daysInMonth: Int = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            val dates: MutableList<DateItem> = ArrayList<DateItem>()
            val month:String = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US)
            (1..daysInMonth).forEach {
                day -> dates.add(DateItem(month, day, 0))
            }
            state.copy(dates, calendar)
        }
    }
}
