package com.potatomeme.ticket_booking_app.data.source.local

import com.potatomeme.ticket_booking_app.domain.entity.SeatEntity
import javax.inject.Inject

class FakeDataSource @Inject constructor() {
    suspend fun requestSeats(id: String): List<SeatEntity> {
        val seatList = mutableListOf<SeatEntity>()
        val numberSeats = 81

        for (i in 0 until numberSeats) {
            val seatStatus =
                if (i == 2 || i == 20 || i == 33 || i == 41 || i == 50 || i == 72 || i == 73) SeatEntity.SeatStatus.UNAVAILABLE else SeatEntity.SeatStatus.AVAILABLE

            seatList.add(SeatEntity(seatStatus, (i + 1).toString()))
        }
        return seatList
    }
}