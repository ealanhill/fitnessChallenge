package me.ealanhill.wtfitnesschallenge.team

import me.ealanhill.wtfitnesschallenge.team.actions.SuperlativeModel
import java.util.*

data class TeamState(val teamName: String = "",
                     val teamMembers: List<TeamMemberModel> = Collections.emptyList(),
                     val superlatives: List<SuperlativeModel> = Collections.emptyList())