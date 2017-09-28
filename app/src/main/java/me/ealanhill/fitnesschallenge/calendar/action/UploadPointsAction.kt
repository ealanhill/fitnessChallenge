package me.ealanhill.wtfitnesschallenge.calendar.action

import me.ealanhill.wtfitnesschallenge.Action
import me.ealanhill.wtfitnesschallenge.calendar.pointsEntry.EntryFormModel

data class UploadPointsAction(val models: List<EntryFormModel>,
                              val year: Int,
                              val month: String,
                              val day: Int): Action
