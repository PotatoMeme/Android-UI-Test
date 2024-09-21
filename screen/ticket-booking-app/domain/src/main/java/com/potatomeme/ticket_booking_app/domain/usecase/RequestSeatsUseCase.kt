package com.potatomeme.ticket_booking_app.domain.usecase

import com.potatomeme.ticket_booking_app.domain.entity.SeatEntity
import com.potatomeme.ticket_booking_app.domain.repository.FakeRepository
import javax.inject.Inject

class RequestSeatsUseCase @Inject constructor(
    private val repository: FakeRepository,
) {
    suspend fun invoke(id: String): List<SeatEntity> {
        return repository.requestSeats(id)
    }
}