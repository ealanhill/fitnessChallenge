package me.ealanhill.wtfitnesschallenge.action

import me.ealanhill.wtfitnesschallenge.pointsEntry.EntryFormItem
import java.util.*

object GetEntryFormItemsAction: Action {
    private var items: List<EntryFormItem> = Collections.emptyList()

    fun create(items: List<EntryFormItem>): GetEntryFormItemsAction {
        this.items = items
        return this
    }

    fun items(): List<EntryFormItem> {
        return this.items
    }
}
