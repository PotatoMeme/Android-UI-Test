package com.potatomeme.ticket_booking_app.data.usecase

import com.potatomeme.ticket_booking_app.domain.entity.FilmEntity
import com.potatomeme.ticket_booking_app.domain.repository.FilmRepository
import com.potatomeme.ticket_booking_app.domain.usecase.RequestFilmsUseCase
import javax.inject.Inject

class RequestFilmsUseCaseImpl @Inject constructor(
    private val filmRepository: FilmRepository,
) : RequestFilmsUseCase {
    override suspend fun invoke(): List<FilmEntity> {
        return filmRepository.requestFilms()
    }
}