package com.potatomeme.ticket_booking_app.data.source.remote

import com.google.firebase.database.FirebaseDatabase
import com.potatomeme.ticket_booking_app.data.model.Film
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class TBAFirebaseSource @Inject constructor(
    private val database: FirebaseDatabase,
) {
    private val ref = database.getReference(DATABASE_NAME)

    suspend fun requestFilms(): FirebaseResult {
        return try {
            val items = mutableListOf<Film>()
            ref.get().await().children.forEach { snapshot ->
                snapshot.getValue(Film::class.java)?.let {
                    items.add(it)
                }
            }
            FirebaseResult.Success(items)
        } catch (e: Exception) {
            FirebaseResult.Failure(e)
        }
    }

    companion object {
        private const val TAG = "TBAFirebaseSource"
        private const val DATABASE_NAME = "items"
    }
}

sealed class FirebaseResult {
    data class Success(val items: List<Film>) : FirebaseResult()
    data class Failure(val exception: Exception) : FirebaseResult()
}