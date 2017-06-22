package me.ealanhill.wtfitnesschallenge.state

import me.ealanhill.wtfitnesschallenge.model.EntryFormModel
import java.util.*

data class PointEntryState(val loading: Boolean = true,
                           val entryFormModels: List<EntryFormModel> = Collections.emptyList())
