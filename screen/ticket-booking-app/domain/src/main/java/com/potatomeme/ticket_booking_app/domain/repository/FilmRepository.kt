package com.potatomeme.ticket_booking_app.domain.repository

import com.potatomeme.ticket_booking_app.domain.entity.FilmEntity

interface FilmRepository {
    suspend fun requestFilms(): List<FilmEntity>
}
