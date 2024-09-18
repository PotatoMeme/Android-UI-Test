package com.potatomeme.ticket_booking_app.domain.usecase

import com.potatomeme.ticket_booking_app.domain.entity.FilmEntity

interface RequestFilmsUseCase {
    suspend fun invoke(): List<FilmEntity>
}