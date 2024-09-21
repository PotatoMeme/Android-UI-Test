package com.potatomeme.ticket_booking_app.domain.entity

data class SeatEntity(val status: SeatStatus, var name: String) {
    enum class SeatStatus {
        AVAILABLE, SELECTED, UNAVAILABLE
    }
}
