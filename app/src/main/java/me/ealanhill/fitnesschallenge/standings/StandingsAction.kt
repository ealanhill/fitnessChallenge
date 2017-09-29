package me.ealanhill.fitnesschallenge.standings

import me.ealanhill.fitnesschallenge.Action
import java.util.*

data class StandingsAction(val team: Map<String, Int> = Collections.emptyMap()): Action