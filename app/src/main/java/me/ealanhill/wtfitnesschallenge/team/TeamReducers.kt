package me.ealanhill.wtfitnesschallenge.team

import me.ealanhill.wtfitnesschallenge.Action
import me.ealanhill.wtfitnesschallenge.team.actions.AddTeamMemberAction
import me.ealanhill.wtfitnesschallenge.team.actions.UpdateTeamNameAction
import me.ealanhill.wtfitnesschallenge.team.actions.UpdateUserMonthTotalAction
import me.tatarka.redux.Reducer
import me.tatarka.redux.Reducers

object TeamReducers {
    fun reducer(): Reducer<Action, TeamState> {
        return Reducers.matchClass<Action, TeamState>()
                .`when`(AddTeamMemberAction::class.java, addTeamMember())
                .`when`(UpdateTeamNameAction::class.java, updateTeamName())
                .`when`(UpdateUserMonthTotalAction::class.java, updateTeamMemberTotal())
    }

    fun addTeamMember(): Reducer<AddTeamMemberAction, TeamState> {
        return Reducer { (teamMember), state ->
            val members = ArrayList<TeamMemberModel>(state.teamMembers)
            members.add(teamMember)
            state.copy(teamMembers = members)
        }
    }

    fun updateTeamName(): Reducer<UpdateTeamNameAction, TeamState> {
        return Reducer { action, state ->
            state.copy(teamName = action.teamName)
        }
    }

    fun updateTeamMemberTotal(): Reducer<UpdateUserMonthTotalAction, TeamState> {
        return Reducer {action, state ->
            val teamMembers = ArrayList<TeamMemberModel>(0)
            copy(state.teamMembers, teamMembers)
            teamMembers.forEach { teamMemberModel: TeamMemberModel ->
                if (teamMemberModel.uid == action.uid) {
                    teamMemberModel.points = action.points
                }
            }
            state.copy(teamMembers = teamMembers)
        }
    }

    private fun copy(oldList: List<TeamMemberModel>, newList: MutableList<TeamMemberModel>) {
        oldList.forEach { teamMemberModel: TeamMemberModel ->
            newList.add(TeamMemberModel(teamMemberModel.uid, teamMemberModel.name,
                    teamMemberModel.points))
        }
    }
}