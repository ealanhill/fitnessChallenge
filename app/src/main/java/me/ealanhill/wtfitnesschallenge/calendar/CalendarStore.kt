package me.ealanhill.wtfitnesschallenge.calendar

import me.ealanhill.wtfitnesschallenge.Action
import me.tatarka.redux.Dispatcher
import me.tatarka.redux.SimpleStore
import me.tatarka.redux.Thunk
import me.tatarka.redux.ThunkDispatcher

class CalendarStore : SimpleStore<CalendarState>(CalendarState()) {
    val dispatcher: Dispatcher<Action, Action> = Dispatcher.forStore(this, CalendarReducers.reducer())
    val thunkDispatcher: Dispatcher<Thunk<Action, Action>, Void> = ThunkDispatcher(dispatcher)

    fun dispatch(action: Action): Action {
        return dispatcher.dispatch(action)
    }

    fun dispatch(thunk: Thunk<Action, Action>) {
        thunkDispatcher.dispatch(thunk)
    }

}