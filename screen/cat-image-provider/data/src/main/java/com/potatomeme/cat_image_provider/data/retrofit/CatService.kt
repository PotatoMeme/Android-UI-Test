package com.potatomeme.cat_image_provider.data.retrofit

import com.potatomeme.cat_image_provider.data.model.Cat
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CatService {
    @GET("images/search")
    suspend fun getCatsToResponse(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("order") order: String
    ): Response<List<Cat>>
}