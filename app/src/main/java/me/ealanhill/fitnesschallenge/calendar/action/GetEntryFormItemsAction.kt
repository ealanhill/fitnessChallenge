package me.ealanhill.wtfitnesschallenge.calendar.action

import me.ealanhill.wtfitnesschallenge.Action
import me.ealanhill.wtfitnesschallenge.calendar.pointsEntry.EntryFormModel
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
