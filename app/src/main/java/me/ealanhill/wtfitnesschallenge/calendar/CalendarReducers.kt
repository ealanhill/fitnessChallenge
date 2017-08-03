package me.ealanhill.wtfitnesschallenge.calendar

import com.google.firebase.database.*
import me.ealanhill.wtfitnesschallenge.action.Action
import me.ealanhill.wtfitnesschallenge.action.InitializeCalendarAction
import me.ealanhill.wtfitnesschallenge.action.UpdateCalendarPointsAction
import me.ealanhill.wtfitnesschallenge.action.UserAction
import me.ealanhill.wtfitnesschallenge.database.DatabaseTables
import me.tatarka.redux.Reducer
import me.tatarka.redux.Reducers
import java.util.*
import kotlin.collections.ArrayList

object CalendarReducers {

    fun reducer(): Reducer<Action, CalendarState> {
        return Reducers.matchClass<Action, CalendarState>()
                .`when`(InitializeCalendarAction::class.java, initializeCalendar())
                .`when`(UserAction::class.java, addNewUserReducer())
                .`when`(UpdateCalendarPointsAction::class.java, updateCalendarPoints())
    }

    fun initializeCalendar(): Reducer<InitializeCalendarAction, CalendarState> {
        return Reducer { _, state ->
            val calendar = state.calendar
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            val daysInMonth: Int = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            val year: Int = calendar.get(Calendar.YEAR)
            val dates: MutableList<DateItem> = ArrayList()
            val month:String = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US)
            (1..daysInMonth).forEach {
                day -> dates.add(DateItem(year, month, day, 0, Collections.emptyMap()))
            }
            state.copy(dateItems = dates, calendar = calendar)
        }
    }

    fun addNewUserReducer(): Reducer<UserAction, CalendarState> {
        return Reducer { (user), state ->
            val database = FirebaseDatabase.getInstance()
                    .getReference(DatabaseTables.ENTRIES)

            database.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError) {
                    // do nothing
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (!dataSnapshot.child(user.uid).exists()) {
                        database.child(user.uid)
                                .updateChildren(mapOf(
                                        "email" to user.email,
                                        "name" to user.displayName))
                    }
                }

            })

            state
        }
    }

    fun updateCalendarPoints(): Reducer<UpdateCalendarPointsAction, CalendarState> {
        return Reducer { action, state ->
            var pointsTotal: Int = 0
            if (action.points()["total"] == null) {
                action.points().forEach { _, value ->
                    pointsTotal += value
                }
            } else {
                pointsTotal = action.points()["total"]!!
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

            state.copy(dateItems = dateItems, calendar = state.calendar)
        }
    }

    private fun copy(oldList: List<DateItem>, newList: MutableList<DateItem>) {
        oldList.forEach { dateItem: DateItem ->
            newList.add(DateItem(dateItem.year, dateItem.month, dateItem.date,
                    dateItem.totalPoints, dateItem.pointsMap))
        }
    }
}
