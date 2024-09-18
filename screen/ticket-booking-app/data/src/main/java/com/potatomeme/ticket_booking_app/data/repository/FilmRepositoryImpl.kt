package com.potatomeme.ticket_booking_app.data.repository

import com.potatomeme.ticket_booking_app.data.TBAMapper
import com.potatomeme.ticket_booking_app.data.source.remote.FirebaseResult
import com.potatomeme.ticket_booking_app.data.source.remote.TBAFirebaseSource
import com.potatomeme.ticket_booking_app.domain.entity.FilmEntity
import com.potatomeme.ticket_booking_app.domain.repository.FilmRepository
import javax.inject.Inject

class FilmRepositoryImpl @Inject constructor(
    private val firebaseSource: TBAFirebaseSource,
) : FilmRepository {
    override suspend fun requestFilms(): List<FilmEntity> {
        return when (val result = firebaseSource.requestFilms()) {
            is FirebaseResult.Failure -> emptyList()
            is FirebaseResult.Success -> TBAMapper.mapperFirebaseFilm(result.items)
        }
    }
}