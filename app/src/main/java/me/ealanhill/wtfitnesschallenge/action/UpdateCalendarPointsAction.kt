package me.ealanhill.wtfitnesschallenge.action

import me.ealanhill.wtfitnesschallenge.DateItem

object UpdateCalendarPointsAction: Action {
    private lateinit var dateItem: DateItem
    private lateinit var points: Map<String, Int>

    fun create(dateItem: DateItem, points: Map<String, Int>): UpdateCalendarPointsAction {
        this.dateItem = dateItem
        this.points = points
        return this
    }

    fun dateItem(): DateItem {
        return this.dateItem
    }

    fun points(): Map<String, Int> {
        return this.points
    }
}
