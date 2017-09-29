package me.ealanhill.fitnesschallenge.team.actions

import me.ealanhill.fitnesschallenge.Action

data class UpdateUserMonthTotalAction(val uid: String, val points: Int): Action