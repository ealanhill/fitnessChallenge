package me.ealanhill.fitnesschallenge.team.actions

import me.ealanhill.fitnesschallenge.Action

object UpdateTeamNameAction: Action {
    var teamName: String = ""

    fun create(name: String): UpdateTeamNameAction {
        this.teamName = name
        return this
    }
}