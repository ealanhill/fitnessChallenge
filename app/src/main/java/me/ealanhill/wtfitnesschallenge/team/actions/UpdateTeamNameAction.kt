package me.ealanhill.wtfitnesschallenge.team.actions

import me.ealanhill.wtfitnesschallenge.Action

object UpdateTeamNameAction: Action {
    var teamName: String = ""

    fun create(name: String): UpdateTeamNameAction {
        this.teamName = name
        return this
    }
}