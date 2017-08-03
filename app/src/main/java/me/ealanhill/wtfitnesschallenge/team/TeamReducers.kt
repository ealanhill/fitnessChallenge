package me.ealanhill.wtfitnesschallenge.team

import me.ealanhill.wtfitnesschallenge.Action
import me.ealanhill.wtfitnesschallenge.team.actions.AddTeamMemberAction
import me.ealanhill.wtfitnesschallenge.team.actions.UpdateTeamNameAction
import me.tatarka.redux.Reducer
import me.tatarka.redux.Reducers

object TeamReducers {
    fun reducer(): Reducer<Action, TeamState> {
        return Reducers.matchClass<Action, TeamState>()
                .`when`(AddTeamMemberAction::class.java, addTeamMember())
                .`when`(UpdateTeamNameAction::class.java, updateTeamName())
    }

    fun addTeamMember(): Reducer<AddTeamMemberAction, TeamState> {
        return Reducer { action, state ->
            val members = ArrayList<TeamMemberModel>(state.teamMembers)
            members.add(action.teamMember)
            state.copy(teamMembers = members)
        }
    }

    fun updateTeamName(): Reducer<UpdateTeamNameAction, TeamState> {
        return Reducer { action, state ->
            state.copy(teamName = action.teamName)
        }
    }
}