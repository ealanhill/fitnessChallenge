package me.ealanhill.wtfitnesschallenge

import junit.framework.Assert.assertTrue
import me.ealanhill.wtfitnesschallenge.team.TeamMemberModel
import me.ealanhill.wtfitnesschallenge.team.TeamReducers
import me.ealanhill.wtfitnesschallenge.team.TeamState
import me.ealanhill.wtfitnesschallenge.team.actions.*
import org.junit.Test

class TeamReducersTest {

    @Test
    fun given_an_empty_list_of_team_members_should_be_able_to_add_a_new_member() {
        val teamMember = TeamMemberModel("0000", "name 1", 0)
        val teamMembers = TeamReducers.addTeamMember()
                .reduce(AddTeamMemberAction(teamMember), TeamState())
                .teamMembers

        assertTrue("Incorrect list size", teamMembers.size == 1)
        assertTrue("Does not include team member", teamMembers.contains(teamMember))
    }

    @Test
    fun given_an_existing_list_of_team_member_should_add_a_new_team_member() {
        val teamMembers = listOf(TeamMemberModel("0000", "name 1", 0),
                TeamMemberModel("0001", "name 2", 0),
                TeamMemberModel("0002", "name 3", 0))

        val teamMember = TeamMemberModel("0003", "name 4", 0)
        val teamMembersReduced = TeamReducers.addTeamMember()
                .reduce(AddTeamMemberAction(teamMember), TeamState(teamMembers = teamMembers))
                .teamMembers

        assertTrue("Incorrect list size", teamMembersReduced.size == 4)
        assertTrue("Does not include team member", teamMembersReduced.contains(teamMember))
    }

    @Test
    fun given_a_team_name_should_update_the_teams_name() {
        val teamName = "Team Name"
        val teamNameReduced = TeamReducers.updateTeamName()
                .reduce(UpdateTeamNameAction.create(teamName), TeamState())
                .teamName

        assertTrue("Inccorect team name", teamNameReduced == teamName)
    }

    @Test
    fun given_a_single_team_member_their_total_should_be_updated() {
        val teamMember = TeamMemberModel("0000", "name 1", 0)
        val teamMemberReduced = TeamReducers.updateTeamMemberTotal()
                .reduce(UpdateUserMonthTotalAction("0000", 10), TeamState(teamMembers = listOf(teamMember)))
                .teamMembers[0]

        assertTrue("Members total is incorrect", teamMemberReduced.points == 10)
    }

    @Test
    fun given_a_list_of_team_members_should_be_able_to_update_a_specific_team_members_points() {
        val teamMembers = listOf(TeamMemberModel("0000", "name 1", 0),
                TeamMemberModel("0001", "name 2", 0),
                TeamMemberModel("0002", "name 3", 0))
        val teamMemberReduced = TeamReducers.updateTeamMemberTotal()
                .reduce(UpdateUserMonthTotalAction("0002", 8), TeamState(teamMembers = teamMembers))
                .teamMembers[2]

        assertTrue("Members total is incorrect", teamMemberReduced.points == 8)
    }

    @Test
    fun given_a_superlative_should_be_inserted_into_the_state() {
        val superlativesReduced = TeamReducers.updateSuperlatives()
                .reduce(SuperlativeAction("Meathead", "0000"), TeamState())
                .superlatives
        assertTrue("Incorrect size", superlativesReduced.size == 1)
        assertTrue("No superlative found", superlativesReduced[0].title == "Meathead" && superlativesReduced[0].value == "0000")
    }

    @Test
    fun given_a_list_of_superlatives_should_insert_new_superlative() {
        val superlativesReduced = TeamReducers.updateSuperlatives()
                .reduce(SuperlativeAction("Meathead", "0000"), TeamState(superlatives = listOf(
                        SuperlativeModel("Snackmeister", "0001"),
                        SuperlativeModel("Lazy", "0002"))))
                .superlatives
        assertTrue("Incorrect size", superlativesReduced.size == 3)
        assertTrue("Missing superlative", superlativesReduced[2].title == "Meathead" && superlativesReduced[2].value == "0000")
    }
}