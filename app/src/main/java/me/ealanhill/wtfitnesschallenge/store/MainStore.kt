package me.ealanhill.wtfitnesschallenge.store

import me.ealanhill.wtfitnesschallenge.action.Action
import me.ealanhill.wtfitnesschallenge.reducers.CalendarReducers
import me.ealanhill.wtfitnesschallenge.state.CalendarState
import me.tatarka.redux.Dispatcher
import me.tatarka.redux.SimpleStore

class MainStore : SimpleStore<CalendarState>(CalendarState()) {
    val dispatcher: Dispatcher<Action, Action> = Dispatcher.forStore(this, CalendarReducers.reducer())

    fun dispatch(action: Action): Action {
        return dispatcher.dispatch(action)
    }

}