package com.potatomeme.ticket_booking_app.presentation.model

import android.os.Parcel
import android.os.Parcelable
import com.potatomeme.ticket_booking_app.domain.entity.CastEntity


data class ParcelableFilm(
    var title: String? = null,
    var description: String? = null,
    var poster: String? = null,
    var time: String? = null,
    var trailer: String? = null,
    var imdb: Int = 0,
    var year: Int = 0,
    var price: Double = 0.0,
    var genre: List<String> = ArrayList(),
    var casts: List<ParcelableCast> = ArrayList(),
):Parcelable{
    constructor(parcel: Parcel) : this(
        title = parcel.readString(),
        description = parcel.readString(),
        poster = parcel.readString(),
        time = parcel.readString(),
        trailer = parcel.readString(),
        imdb = parcel.readInt(),
        year = parcel.readInt(),
        price = parcel.readDouble(),
        genre = parcel.createStringArrayList()?: ArrayList(),
        casts = parcel.createTypedArrayList(ParcelableCast.CREATOR)?: ArrayList()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(title)
        dest.writeString(description)
        dest.writeString(poster)
        dest.writeString(time)
        dest.writeString(trailer)
        dest.writeInt(imdb)
        dest.writeInt(year)
        dest.writeDouble(price)
        dest.writeStringList(genre)
        dest.writeTypedList(casts)
    }

    companion object CREATOR : Parcelable.Creator<ParcelableFilm> {
        override fun createFromParcel(parcel: Parcel): ParcelableFilm {
            return ParcelableFilm(parcel)
        }

        override fun newArray(size: Int): Array<ParcelableFilm?> {
            return arrayOfNulls(size)
        }
    }

}
