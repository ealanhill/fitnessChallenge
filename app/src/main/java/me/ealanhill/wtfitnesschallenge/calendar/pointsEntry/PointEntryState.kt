package me.ealanhill.wtfitnesschallenge.calendar.pointsEntry

import java.util.*

data class PointEntryState(val loading: Boolean = true,
                           val entryFormModels: List<EntryFormModel> = Collections.emptyList())
