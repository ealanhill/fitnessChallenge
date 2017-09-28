package me.ealanhill.wtfitnesschallenge.team.actions

import me.ealanhill.wtfitnesschallenge.Action
import me.ealanhill.wtfitnesschallenge.team.TeamMemberModel

data class AddTeamMemberAction(val teamMember: TeamMemberModel = TeamMemberModel("", "", 0)): Action