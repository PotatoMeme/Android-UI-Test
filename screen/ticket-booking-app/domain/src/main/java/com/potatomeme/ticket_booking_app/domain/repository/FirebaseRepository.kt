package com.potatomeme.ticket_booking_app.domain.repository

import com.potatomeme.ticket_booking_app.domain.entity.BannerEntity
import com.potatomeme.ticket_booking_app.domain.entity.FilmEntity

interface FirebaseRepository {
    suspend fun requestTopFilms(): List<FilmEntity>
    suspend fun requestUpcomingFilms(): List<FilmEntity>
    suspend fun requestBanners(): List<BannerEntity>
}
