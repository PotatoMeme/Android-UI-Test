package com.potatomeme.ticket_booking_app.presentation.model

import android.os.Parcel
import android.os.Parcelable

data class ParcelableCast(
    var picUrl: String? = null,
    var actor: String? = null,
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(picUrl)
        parcel.writeString(actor)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ParcelableCast> {
        override fun createFromParcel(parcel: Parcel): ParcelableCast {
            return ParcelableCast(parcel)
        }

        override fun newArray(size: Int): Array<ParcelableCast?> {
            return arrayOfNulls(size)
        }
    }

}
