package me.ealanhill.wtfitnesschallenge.reducers

import com.google.firebase.database.FirebaseDatabase
import me.ealanhill.wtfitnesschallenge.DateItem
import me.ealanhill.wtfitnesschallenge.action.Action
import me.ealanhill.wtfitnesschallenge.action.InitializeCalendarAction
import me.ealanhill.wtfitnesschallenge.action.UpdateCalendarPointsAction
import me.ealanhill.wtfitnesschallenge.action.UploadPointsAction
import me.ealanhill.wtfitnesschallenge.pointsEntry.EntryFormItem
import me.ealanhill.wtfitnesschallenge.state.CalendarState
import me.tatarka.redux.Reducer
import me.tatarka.redux.Reducers
import java.util.*
import kotlin.collections.ArrayList

object CalendarReducers {

    fun reducer(): Reducer<Action, CalendarState> {
        return Reducers.matchClass<Action, CalendarState>()
                .`when`(InitializeCalendarAction::class.java, initializeCalendar())
                .`when`(UpdateCalendarPointsAction::class.java, updateCalendarPoints())
                .`when`(UploadPointsAction::class.java, updateFirebaseDatabase())
    }

    fun initializeCalendar(): Reducer<InitializeCalendarAction, CalendarState> {
        return Reducer { _, state ->
            val calendar = state.calendar
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            val daysInMonth: Int = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            val dates: MutableList<DateItem> = ArrayList<DateItem>()
            val month:String = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US)
            (1..daysInMonth).forEach {
                day -> dates.add(DateItem(month, day, 0, Collections.emptyMap()))
            }
            state.copy(dates, calendar)
        }
    }

    fun updateCalendarPoints(): Reducer<UpdateCalendarPointsAction, CalendarState> {
        return Reducer { action, state ->
            var pointsTotal: Int = 0
            action.points().forEach { _, value ->
                pointsTotal += value
            }

            val dateItems = ArrayList<DateItem>()
            copy(state.dateItems, dateItems)

            dateItems.forEach { dateItem: DateItem ->
                if (dateItem.date == action.dateItem().date) {
                    dateItem.totalPoints = pointsTotal
                    dateItem.pointsMap = action.points()
                    return@forEach
                }
            }

            state.copy(dateItems, state.calendar)
        }
    }

    fun updateFirebaseDatabase(): Reducer<UploadPointsAction, CalendarState> {
        return Reducer { action, state ->
            val database = FirebaseDatabase.getInstance().getReference(action.month).child(Integer.toString(action.day))
            var total = 0
            action.items.forEach { entryFormItem: EntryFormItem ->
                database.child("user 1").child(entryFormItem.name).setValue(entryFormItem.value)
                total += entryFormItem.value
            }
            database.child("user 1").child("total").setValue(total)

            state
        }
    }

    private fun copy(oldList: List<DateItem>, newList: MutableList<DateItem>) {
        oldList.forEach { dateItem: DateItem ->
            newList.add(DateItem(dateItem.month, dateItem.date, dateItem.totalPoints, dateItem.pointsMap))
        }
    }
}
