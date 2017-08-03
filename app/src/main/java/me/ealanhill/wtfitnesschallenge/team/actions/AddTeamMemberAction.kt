package me.ealanhill.wtfitnesschallenge.team.actions

import me.ealanhill.wtfitnesschallenge.Action
import me.ealanhill.wtfitnesschallenge.team.TeamMemberModel

object AddTeamMemberAction: Action {
    var teamMember: TeamMemberModel = TeamMemberModel("", 0)

    fun create(name: String, points: Int): AddTeamMemberAction {
        this.teamMember = TeamMemberModel(name, points)
        return this
    }
}