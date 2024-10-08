package com.potatomeme.chirang_note_app.presentation_xml.model

import android.os.Parcel
import android.os.Parcelable

data class ParcelableNote(
    val id: Int = 0,
    val title: String?,
    val dateTime: String?,
    val subtitle: String?,
    val noteText: String?,
    val imagePath: String?,
    val color: String?,
    val webLink: String?,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(dateTime)
        parcel.writeString(subtitle)
        parcel.writeString(noteText)
        parcel.writeString(imagePath)
        parcel.writeString(color)
        parcel.writeString(webLink)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "$title：$dateTime"
    }

    companion object CREATOR : Parcelable.Creator<ParcelableNote> {
        override fun createFromParcel(parcel: Parcel): ParcelableNote {
            return ParcelableNote(parcel)
        }

        override fun newArray(size: Int): Array<ParcelableNote?> {
            return arrayOfNulls(size)
        }
    }
}