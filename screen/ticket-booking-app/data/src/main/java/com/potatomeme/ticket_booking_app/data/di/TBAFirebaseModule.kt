package com.potatomeme.ticket_booking_app.data.di

import com.google.firebase.database.FirebaseDatabase
import com.potatomeme.ticket_booking_app.data.source.remote.TBAFirebaseSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TBAFirebaseModule {
    @Provides
    fun provideFirebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    @Provides
    fun provideTBAFirebaseSource(
        firebaseDatabase: FirebaseDatabase,
    ): TBAFirebaseSource {
        return TBAFirebaseSource(firebaseDatabase)
    }
}