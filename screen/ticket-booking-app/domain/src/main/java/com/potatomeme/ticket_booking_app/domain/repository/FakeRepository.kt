package com.potatomeme.ticket_booking_app.domain.repository

import com.potatomeme.ticket_booking_app.domain.entity.SeatEntity

interface FakeRepository {
    suspend fun requestSeats(id: String): List<SeatEntity>
}