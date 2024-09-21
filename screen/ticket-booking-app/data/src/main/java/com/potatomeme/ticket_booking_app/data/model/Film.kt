package com.potatomeme.ticket_booking_app.data.model

data class Film(
    var title: String? = null,
    var description: String? = null,
    var poster: String? = null,
    var time: String? = null,
    var trailer: String? = null,
    var imdb: Int = 0,
    var year: Int = 0,
    var price: Double = 0.0,
    var genre: List<String> = ArrayList(),
    var casts: List<Cast> = ArrayList(),
)
