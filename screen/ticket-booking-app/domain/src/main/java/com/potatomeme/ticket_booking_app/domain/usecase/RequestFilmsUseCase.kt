package com.potatomeme.ticket_booking_app.domain.usecase

import com.potatomeme.ticket_booking_app.domain.entity.FilmEntity
import com.potatomeme.ticket_booking_app.domain.repository.FilmRepository
import javax.inject.Inject

class RequestFilmsUseCase @Inject constructor(
    private val filmRepository: FilmRepository,
) {
    suspend fun invoke(): List<FilmEntity> {
        return filmRepository.requestFilms()
    }
}