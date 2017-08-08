package me.ealanhill.wtfitnesschallenge.standings

import me.ealanhill.wtfitnesschallenge.Action
import me.tatarka.redux.Reducer
import me.tatarka.redux.Reducers

object StandingsReducers {

    fun reducer(): Reducer<Action, StandingsState> {
        return Reducers.matchClass<Action, StandingsState>()
                .`when`(StandingsAction::class.java, sortTeamStandings())
    }

    fun sortTeamStandings(): Reducer<StandingsAction, StandingsState> {
        return Reducer { (teams), _ ->
            val sortedTeams = teams.toList().sortedByDescending { (_, value) -> value }.toMap()
            StandingsState(sortedTeams)
        }
    }
}