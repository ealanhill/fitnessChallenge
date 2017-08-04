package me.ealanhill.wtfitnesschallenge.team.actions

import me.ealanhill.wtfitnesschallenge.Action

data class UpdateUserMonthTotalAction(val uid: String, val points: Int): Action