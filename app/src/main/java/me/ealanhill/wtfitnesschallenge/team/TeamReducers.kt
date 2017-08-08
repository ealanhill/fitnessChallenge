package me.ealanhill.wtfitnesschallenge.team

import me.ealanhill.wtfitnesschallenge.Action
import me.ealanhill.wtfitnesschallenge.team.actions.*
import me.tatarka.redux.Reducer
import me.tatarka.redux.Reducers

object TeamReducers {
    fun reducer(): Reducer<Action, TeamState> {
        return Reducers.matchClass<Action, TeamState>()
                .`when`(AddTeamMemberAction::class.java, addTeamMember())
                .`when`(UpdateTeamNameAction::class.java, updateTeamName())
                .`when`(UpdateUserMonthTotalAction::class.java, updateTeamMemberTotal())
                .`when`(SuperlativeAction::class.java, updateSuperlatives())
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
        return Reducer {(uid, points), state ->
            val teamMembers = ArrayList<TeamMemberModel>(0)
            copy(state.teamMembers, teamMembers)
            teamMembers.forEach { teamMemberModel: TeamMemberModel ->
                if (teamMemberModel.uid == uid) {
                    teamMemberModel.points = points
                }
            }
            state.copy(teamMembers = teamMembers)
        }
    }

    fun updateSuperlatives(): Reducer<SuperlativeAction, TeamState> {
        return Reducer { (title, value), state ->
            val superlatives = HashMap<String, String>(0)
            state.superlatives.forEach { (title, value): SuperlativeModel ->
                superlatives.put(title, value)
            }

            superlatives.put(title, value)

            val superlativeList = ArrayList<SuperlativeModel>(0)
            superlatives.forEach { superlativeTitle, superlativeValue ->
                superlativeList.add(SuperlativeModel(superlativeTitle, superlativeValue))
            }

            state.copy(superlatives = superlativeList)
        }
    }

    private fun copy(oldList: List<TeamMemberModel>, newList: MutableList<TeamMemberModel>) {
        oldList.forEach { (uid, name, points): TeamMemberModel ->
            newList.add(TeamMemberModel(uid, name, points))
        }
    }
}