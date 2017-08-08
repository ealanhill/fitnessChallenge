package me.ealanhill.wtfitnesschallenge.standings

import me.ealanhill.wtfitnesschallenge.Action
import java.util.*

data class StandingsAction(val team: Map<String, Int> = Collections.emptyMap()): Action