package com.potatomeme.ticket_booking_app.domain.usecase

import com.potatomeme.ticket_booking_app.domain.repository.FakeRepository
import javax.inject.Inject

class RequestTimeSlotsUseCase @Inject constructor(
    private val repository: FakeRepository,
) {
    suspend fun invoke(id: String, date: String): List<String> {
        return repository.requestTimeSlots(id, date)
    }
}