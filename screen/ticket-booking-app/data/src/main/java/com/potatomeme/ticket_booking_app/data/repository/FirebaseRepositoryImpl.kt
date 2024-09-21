package com.potatomeme.ticket_booking_app.data.repository

import com.potatomeme.ticket_booking_app.data.mapper.TBADataToDomainMapper
import com.potatomeme.ticket_booking_app.data.source.remote.FirebaseResult
import com.potatomeme.ticket_booking_app.data.source.remote.TBAFirebaseSource
import com.potatomeme.ticket_booking_app.domain.entity.BannerEntity
import com.potatomeme.ticket_booking_app.domain.entity.FilmEntity
import com.potatomeme.ticket_booking_app.domain.repository.FirebaseRepository
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseSource: TBAFirebaseSource,
) : FirebaseRepository {
    override suspend fun requestTopFilms(): List<FilmEntity> {
        return when (val result = firebaseSource.requestTopFilms()) {
            is FirebaseResult.Failure -> emptyList()
            is FirebaseResult.Success -> TBADataToDomainMapper.mapperFirebaseFilm(result.items)
        }
    }

    override suspend fun requestUpcomingFilms(): List<FilmEntity> {
        return when (val result = firebaseSource.requestUpcomingFilms()) {
            is FirebaseResult.Failure -> emptyList()
            is FirebaseResult.Success -> TBADataToDomainMapper.mapperFirebaseFilm(result.items)
        }
    }

    override suspend fun requestBanners(): List<BannerEntity> {
        return when (val result = firebaseSource.requestBanners()) {
            is FirebaseResult.Failure -> emptyList()
            is FirebaseResult.Success -> TBADataToDomainMapper.mapperFirebaseBanner(result.items)
        }
    }
}