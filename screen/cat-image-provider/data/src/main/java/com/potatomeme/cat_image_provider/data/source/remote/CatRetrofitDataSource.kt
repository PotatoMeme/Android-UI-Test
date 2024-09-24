package com.potatomeme.cat_image_provider.data.source.remote

import com.potatomeme.cat_image_provider.data.model.Cat
import com.potatomeme.cat_image_provider.data.retrofit.CatService
import retrofit2.Response
import javax.inject.Inject

class CatRetrofitDataSource @Inject constructor(
    private val catService: CatService,
) {
    suspend fun requestCats(limit: Int, page: Int, order: String) : Response<List<Cat>> = catService.getCatsToResponse(limit, page, order)
}