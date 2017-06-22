package me.ealanhill.wtfitnesschallenge.action

import me.ealanhill.wtfitnesschallenge.model.EntryFormModel

data class UploadPointsAction(val models: List<EntryFormModel>,
                              val month: String,
                              val day: Int): Action
