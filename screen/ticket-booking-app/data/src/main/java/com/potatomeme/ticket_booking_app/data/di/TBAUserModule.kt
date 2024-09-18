package com.potatomeme.ticket_booking_app.data.di

import com.potatomeme.ticket_booking_app.data.repository.FilmRepositoryImpl
import com.potatomeme.ticket_booking_app.domain.repository.FilmRepository
import com.potatomeme.ticket_booking_app.domain.usecase.RequestFilmsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TBAUserModule {
    @Binds
    abstract fun bindFilmRepository(repository: FilmRepositoryImpl): FilmRepository
}