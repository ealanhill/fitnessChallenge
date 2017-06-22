package me.ealanhill.wtfitnesschallenge.action

import me.ealanhill.wtfitnesschallenge.model.EntryFormModel
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
