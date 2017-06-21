package me.ealanhill.wtfitnesschallenge.store

import me.ealanhill.wtfitnesschallenge.action.Action
import me.ealanhill.wtfitnesschallenge.reducers.PointEntryReducers
import me.ealanhill.wtfitnesschallenge.state.PointEntryState
import me.tatarka.redux.Dispatcher
import me.tatarka.redux.SimpleStore
import me.tatarka.redux.Thunk
import me.tatarka.redux.ThunkDispatcher

class PointEntryStore : SimpleStore<PointEntryState>(PointEntryState()) {
    val dispatcher: Dispatcher<Action, Action> = Dispatcher.forStore(this, PointEntryReducers.reducer())
    val thunkDispatcher: Dispatcher<Thunk<Action, Action>, Void> = ThunkDispatcher(dispatcher)

    fun dispatch(action: Action): Action {
        return dispatcher.dispatch(action)
    }

    fun dispatch(thunk: Thunk<Action, Action>) {
        thunkDispatcher.dispatch(thunk)
    }

}
