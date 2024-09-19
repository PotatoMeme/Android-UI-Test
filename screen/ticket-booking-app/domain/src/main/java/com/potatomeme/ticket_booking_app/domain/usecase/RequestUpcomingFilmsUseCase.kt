package com.potatomeme.ticket_booking_app.domain.usecase

import com.potatomeme.ticket_booking_app.domain.entity.FilmEntity
import com.potatomeme.ticket_booking_app.domain.repository.FirebaseRepository
import javax.inject.Inject

class RequestUpcomingFilmsUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
) {
    suspend fun invoke(): List<FilmEntity> {
        return firebaseRepository.requestUpcomingFilms()
    }
}