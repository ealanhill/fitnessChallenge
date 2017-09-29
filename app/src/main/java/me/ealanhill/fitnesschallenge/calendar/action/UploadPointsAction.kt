package me.ealanhill.fitnesschallenge.calendar.action

import me.ealanhill.fitnesschallenge.Action
import me.ealanhill.fitnesschallenge.calendar.pointsEntry.EntryFormModel

data class UploadPointsAction(val models: List<EntryFormModel>,
                              val year: Int,
                              val month: String,
                              val day: Int): Action
