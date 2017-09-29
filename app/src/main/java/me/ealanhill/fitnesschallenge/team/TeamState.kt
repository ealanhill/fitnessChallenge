package me.ealanhill.fitnesschallenge.team

import me.ealanhill.fitnesschallenge.team.actions.SuperlativeModel
import java.util.*

data class TeamState(val teamName: String = "",
                     val teamMembers: List<TeamMemberModel> = Collections.emptyList(),
                     val superlatives: List<SuperlativeModel> = Collections.emptyList())