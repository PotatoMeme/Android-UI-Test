package com.potatomeme.ticket_booking_app.data.di

import com.potatomeme.ticket_booking_app.data.repository.FirebaseRepositoryImpl
import com.potatomeme.ticket_booking_app.domain.repository.FirebaseRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TBAUserModule {
    @Binds
    abstract fun bindFirebaseRepository(repository: FirebaseRepositoryImpl): FirebaseRepository
}