package com.potatomeme.ticket_booking_app.data.repository

import com.potatomeme.ticket_booking_app.data.source.local.FakeDataSource
import com.potatomeme.ticket_booking_app.domain.entity.SeatEntity
import com.potatomeme.ticket_booking_app.domain.repository.FakeRepository
import javax.inject.Inject

class FakeRepositoryImpl @Inject constructor(
    private val fakeDataSource: FakeDataSource
): FakeRepository {
    override suspend fun requestSeats(id: String): List<SeatEntity> {
        return fakeDataSource.requestSeats(id)
    }
}