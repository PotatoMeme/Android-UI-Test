package com.potatomeme.ticket_booking_app.data.source.local

import com.potatomeme.ticket_booking_app.domain.entity.SeatEntity
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
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
    suspend fun requestTimeSlots(id: String,date:String): List<String> {
        val timeSlots = mutableListOf<String>()
        val formatter = DateTimeFormatter.ofPattern("hh:mm a")

        for (i in 0 until 24 step 2) {
            val time = LocalTime.of(i, 0)
            timeSlots.add(time.format(formatter))
        }
        return timeSlots
    }

    suspend fun requestDateSlots(id: String): List<String> {
        val dates = mutableListOf<String>()
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("EEE/dd/MMM")

        for (i in 0 until 7) {
            dates.add(today.plusDays(i.toLong()).format(formatter))
        }
        return dates
    }
}