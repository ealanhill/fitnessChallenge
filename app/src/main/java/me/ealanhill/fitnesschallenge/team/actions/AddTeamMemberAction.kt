package me.ealanhill.fitnesschallenge.team.actions

import me.ealanhill.fitnesschallenge.Action
import me.ealanhill.fitnesschallenge.team.TeamMemberModel

data class AddTeamMemberAction(val teamMember: TeamMemberModel = TeamMemberModel("", "", 0)): Action