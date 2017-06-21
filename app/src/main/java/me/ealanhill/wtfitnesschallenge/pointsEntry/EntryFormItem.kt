package me.ealanhill.wtfitnesschallenge.pointsEntry

import android.os.Parcel
import android.os.Parcelable

data class EntryFormItem(
        val name: String = "",
        val label: String = "",
        val type: String = "",
        val operation: String = "",
        var value: Int = 0): Parcelable {

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
