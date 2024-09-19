package com.potatomeme.ticket_booking_app.domain.usecase

import com.potatomeme.ticket_booking_app.domain.entity.BannerEntity
import com.potatomeme.ticket_booking_app.domain.entity.FilmEntity
import com.potatomeme.ticket_booking_app.domain.repository.FirebaseRepository
import javax.inject.Inject

class RequestBannersUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
) {
    suspend fun invoke(): List<BannerEntity> {
        return firebaseRepository.requestBanners()
    }
}