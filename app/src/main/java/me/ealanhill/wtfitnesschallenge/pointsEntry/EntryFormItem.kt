package me.ealanhill.wtfitnesschallenge.pointsEntry

import com.squareup.moshi.Json

data class EntryFormItem(
        @Json(name = "name") val name: String = "",
        @Json(name = "label") val label: String = "",
        @Json(name = "type") val type: String = "",
        @Json(name = "value") var value: Int = 0)
