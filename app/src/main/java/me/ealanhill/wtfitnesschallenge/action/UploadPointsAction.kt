package me.ealanhill.wtfitnesschallenge.action

import me.ealanhill.wtfitnesschallenge.pointsEntry.EntryFormItem

data class UploadPointsAction(val items: List<EntryFormItem>,
                              val month: String,
                              val day: Int): Action
