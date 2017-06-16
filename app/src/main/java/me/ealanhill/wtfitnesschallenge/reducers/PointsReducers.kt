package me.ealanhill.wtfitnesschallenge.reducers

import me.ealanhill.wtfitnesschallenge.action.Action
import me.ealanhill.wtfitnesschallenge.action.UpdatePointsAction
import me.ealanhill.wtfitnesschallenge.state.PointsState
import me.tatarka.redux.Reducer
import me.tatarka.redux.Reducers

object PointsReducers {

    fun reducer(): Reducer<Action, PointsState> {
        return Reducers.matchClass<Action, PointsState>()
                .`when`(UpdatePointsAction::class.java, updatePoints())
    }

    fun updatePoints(): Reducer<UpdatePointsAction, PointsState> {
        return Reducer { action, state ->
            state.copy(state.pointsMap
                    .plus(Pair(action.field(), action.points())))
        }
    }
}
