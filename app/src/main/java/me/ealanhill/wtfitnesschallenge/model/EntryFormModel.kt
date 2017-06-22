package me.ealanhill.wtfitnesschallenge.model

import android.os.Parcel
import android.os.Parcelable

data class EntryFormModel(
        val name: String = "",
        val label: String = "",
        val type: String = "",
        val operation: String = "",
        var value: Int = 0): Parcelable {

    companion object {
        @JvmField
        @Suppress("unused")
        val CREATOR: Parcelable.Creator<EntryFormModel> = object : Parcelable.Creator<EntryFormModel> {
            override fun createFromParcel(source: Parcel): EntryFormModel {
                return EntryFormModel(source)
            }

            override fun newArray(size: Int): Array<EntryFormModel> {
                return Array(size, {_ -> EntryFormModel() })
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
