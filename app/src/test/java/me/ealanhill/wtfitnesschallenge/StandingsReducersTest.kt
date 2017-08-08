package me.ealanhill.wtfitnesschallenge

import junit.framework.Assert.assertTrue
import me.ealanhill.wtfitnesschallenge.standings.StandingsAction
import me.ealanhill.wtfitnesschallenge.standings.StandingsReducers
import me.ealanhill.wtfitnesschallenge.standings.StandingsState
import org.junit.Test

class StandingsReducersTest {

    @Test
    fun provided_a_list_of_teams_with_different_average_points_should_sort_in_descending_order() {
        val teams = mapOf("team 1" to 1, "team 2" to 2, "team 3" to 3)
        val teamsSorted = mapOf("team 3" to 3, "team 2" to 2, "team 1" to 1)
        val standingsState = StandingsState()
        val reducedTeams = StandingsReducers.sortTeamStandings()
                                            .reduce(StandingsAction(teams), standingsState)
                                            .teams

        assertTrue(reducedTeams == teamsSorted)
    }

    @Test
    fun provided_a_list_of_teams_with_same_average_points_should_sort_by_team_name() {
        val teams = mapOf("team 1" to 1, "team 2" to 1, "team 3" to 1)
        val teamsSorted = mapOf("team 3" to 1, "team 2" to 1, "team 1" to 1)
        val standingsState = StandingsState()
        val reducedTeams = StandingsReducers.sortTeamStandings()
                .reduce(StandingsAction(teams), standingsState)
                .teams

        assertTrue(reducedTeams == teamsSorted)
    }

    @Test
    fun provided_a_list_of_teams_with_different_points_should_sort_correctly() {
        val teams = mapOf("team 1" to 1, "team 2" to 1, "team 3" to 3)
        val teamsSorted = mapOf("team 3" to 3, "team 2" to 1, "team 1" to 1)
        val standingsState = StandingsState()
        val reducedTeams = StandingsReducers.sortTeamStandings()
                .reduce(StandingsAction(teams), standingsState)
                .teams

        assertTrue(reducedTeams == teamsSorted)
    }
}