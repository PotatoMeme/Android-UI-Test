package com.potatomeme.ticket_booking_app.presentation.mapper

import com.potatomeme.ticket_booking_app.domain.entity.CastEntity
import com.potatomeme.ticket_booking_app.domain.entity.FilmEntity
import com.potatomeme.ticket_booking_app.presentation.model.ParcelableCast
import com.potatomeme.ticket_booking_app.presentation.model.ParcelableFilm

object DomainEntityParcelableMapper {
    fun mapToParcelable(entity: FilmEntity): ParcelableFilm {
        return ParcelableFilm(
            title = entity.title,
            description = entity.description,
            poster = entity.poster,
            time = entity.time,
            trailer = entity.trailer,
            imdb = entity.imdb,
            year = entity.year,
            price = entity.price,
            genre = entity.genre,
            casts = entity.casts.map { mapToParcelable(it) }
        )
    }

    private fun mapToParcelable(castEntity: CastEntity) : ParcelableCast {
        return ParcelableCast(
            picUrl = castEntity.picUrl,
            actor = castEntity.actor
        )
    }
}