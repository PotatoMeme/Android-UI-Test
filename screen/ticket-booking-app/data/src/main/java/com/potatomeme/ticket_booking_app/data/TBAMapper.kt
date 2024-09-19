package com.potatomeme.ticket_booking_app.data

import com.potatomeme.ticket_booking_app.data.model.Banner
import com.potatomeme.ticket_booking_app.data.model.Cast
import com.potatomeme.ticket_booking_app.data.model.Film
import com.potatomeme.ticket_booking_app.domain.entity.BannerEntity
import com.potatomeme.ticket_booking_app.domain.entity.CastEntity
import com.potatomeme.ticket_booking_app.domain.entity.FilmEntity

object TBAMapper {
    fun mapperFirebaseFilm(response: List<Film>): List<FilmEntity> {
        return response.toFilmEntity()
    }

    private fun List<Film>.toFilmEntity(): List<FilmEntity> {
        return this.map {
            FilmEntity(
                title = it.title,
                description = it.description,
                poster = it.poster,
                time = it.time,
                trailer = it.trailer,
                imdb = it.imdb,
                year = it.year,
                price = it.price,
                genre = it.genre,
                casts = it.casts.toCastEntity()
            )
        }
    }

    private fun List<Cast>.toCastEntity(): List<CastEntity> {
        return this.map {
            CastEntity(
                picUrl = it.picUrl,
                actor = it.actor
            )
        }
    }

    fun mapperFirebaseBanner(response: List<Banner>): List<BannerEntity> {
        return response.toBannerEntity()
    }

    private fun List<Banner>.toBannerEntity(): List<BannerEntity> {
        return this.map {
            BannerEntity(
                image = it.image
            )
        }
    }
}