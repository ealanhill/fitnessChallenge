package me.ealanhill.fitnesschallenge.calendar.action

import me.ealanhill.fitnesschallenge.Action
import me.ealanhill.fitnesschallenge.calendar.DateItem

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
