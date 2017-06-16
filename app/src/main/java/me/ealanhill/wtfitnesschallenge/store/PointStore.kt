package me.ealanhill.wtfitnesschallenge.store

import me.ealanhill.wtfitnesschallenge.action.Action
import me.ealanhill.wtfitnesschallenge.reducers.PointsReducers
import me.ealanhill.wtfitnesschallenge.state.PointsState
import me.tatarka.redux.Dispatcher
import me.tatarka.redux.SimpleStore

class PointStore: SimpleStore<PointsState>(PointsState()) {
    val dispatcher: Dispatcher<Action, Action> = Dispatcher.forStore(this, PointsReducers.reducer())

    fun dispatch(action: Action): Action {
        return dispatcher.dispatch(action)
    }
}
