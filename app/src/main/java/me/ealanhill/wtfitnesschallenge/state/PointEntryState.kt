package me.ealanhill.wtfitnesschallenge.state

import me.ealanhill.wtfitnesschallenge.pointsEntry.EntryFormItem
import java.util.*

data class PointEntryState(val loading: Boolean = true,
                           val entryFormItems: List<EntryFormItem> = Collections.emptyList())
