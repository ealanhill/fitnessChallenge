package me.ealanhill.wtfitnesschallenge.action

object UpdatePointsAction: Action {

    private var field: String = ""
    private var points: Int = 0

    fun create(field: String, points: Int): UpdatePointsAction {
        this.field = field
        this.points = points
        return this
    }

    fun field(): String {
        return this.field
    }

    fun points(): Int {
        return this.points
    }
}