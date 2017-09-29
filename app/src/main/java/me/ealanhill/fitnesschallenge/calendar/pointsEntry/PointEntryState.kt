package me.ealanhill.fitnesschallenge.calendar.pointsEntry

import java.util.*

data class PointEntryState(val loading: Boolean = true,
                           val entryFormModels: List<EntryFormModel> = Collections.emptyList())
