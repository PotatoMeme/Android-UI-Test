package com.potatomeme.ticket_booking_app.data.source.remote

import com.google.firebase.database.FirebaseDatabase
import com.potatomeme.ticket_booking_app.data.model.Film
import com.potatomeme.ticket_booking_app.data.model.Banner
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class TBAFirebaseSource @Inject constructor(
    private val database: FirebaseDatabase,
) {
    suspend fun requestFilms(): FirebaseResult<Film> {
        return try {
            val items = mutableListOf<Film>()
            val ref = database.getReference(DATABASE_NAME_ITEMS)
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

    suspend fun requestBanners(): FirebaseResult<Banner> {
        return try {
            val items = mutableListOf<Banner>()
            val ref = database.getReference(DATABASE_NAME_BANNERS)
            ref.get().await().children.forEach { snapshot ->
                snapshot.getValue(Banner::class.java)?.let {
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
        private const val DATABASE_NAME_ITEMS = "items"
        private const val DATABASE_NAME_BANNERS = "banners"
    }
}

sealed class FirebaseResult<out T> {
    data class Success<T>(val items: List<T>) : FirebaseResult<T>()
    data class Failure<T>(val exception: Exception) : FirebaseResult<T>()
}