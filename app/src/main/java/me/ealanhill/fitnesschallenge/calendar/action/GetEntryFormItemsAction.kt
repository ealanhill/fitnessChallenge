package me.ealanhill.fitnesschallenge.calendar.action

import me.ealanhill.fitnesschallenge.Action
import me.ealanhill.fitnesschallenge.calendar.pointsEntry.EntryFormModel
import java.util.*

object GetEntryFormItemsAction: Action {
    private var models: List<EntryFormModel> = Collections.emptyList()

    fun create(models: List<EntryFormModel>): GetEntryFormItemsAction {
        this.models = models
        return this
    }

    fun items(): List<EntryFormModel> {
        return this.models
    }
}
