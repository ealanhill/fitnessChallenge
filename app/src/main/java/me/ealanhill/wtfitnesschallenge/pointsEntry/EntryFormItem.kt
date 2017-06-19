package me.ealanhill.wtfitnesschallenge.pointsEntry

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class EntryFormItem(
        @Json(name = "name") val name: String = "",
        @Json(name = "label") val label: String = "",
        @Json(name = "type") val type: String = "",
        @Json(name = "operation") val operation: String = "",
        @Json(name = "value") var value: Int = 0): Parcelable {

    companion object {
        @JvmField
        @Suppress("unused")
        val CREATOR: Parcelable.Creator<EntryFormItem> = object : Parcelable.Creator<EntryFormItem> {
            override fun createFromParcel(source: Parcel): EntryFormItem {
                return EntryFormItem(source)
            }

            override fun newArray(size: Int): Array<EntryFormItem> {
                return Array(size, {_ -> EntryFormItem() })
            }
        }
    }

    protected constructor(parcelIn: Parcel) : this(
            parcelIn.readString(),
            parcelIn.readString(),
            parcelIn.readString(),
            parcelIn.readString(),
            parcelIn.readInt()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeString(label)
        dest.writeString(type)
        dest.writeString(operation)
        dest.writeInt(value)
    }

    override fun describeContents() = 0
}
