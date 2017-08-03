package me.ealanhill.wtfitnesschallenge.team

import java.util.*

data class TeamState(val teamMembers: List<TeamMemberModel> = Collections.emptyList(),
                     val superlatives: List<SuperlativeModel> = Collections.emptyList())