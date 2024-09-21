package com.potatomeme.ticket_booking_app.domain.usecase

import com.potatomeme.ticket_booking_app.domain.repository.FakeRepository
import javax.inject.Inject

class RequestDateSlotsUseCase @Inject constructor(
    private val repository: FakeRepository,
) {
    suspend fun invoke(id: String): List<String> {
        return repository.requestDateSlots(id)
    }
}