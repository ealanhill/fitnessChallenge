package me.ealanhill.wtfitnesschallenge.action

import me.ealanhill.wtfitnesschallenge.model.EntryFormModel

data class UploadPointsAction(val models: List<EntryFormModel>,
                              val year: Int,
                              val month: String,
                              val day: Int): Action
