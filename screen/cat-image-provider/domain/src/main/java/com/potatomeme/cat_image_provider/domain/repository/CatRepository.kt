package com.potatomeme.cat_image_provider.domain.repository

import androidx.paging.PagingData
import com.potatomeme.cat_image_provider.domain.entity.CatEntity
import kotlinx.coroutines.flow.Flow

interface CatRepository {
    //retrofit
    suspend fun requestCats(limit: Int, page: Int, order: String): Result<List<CatEntity>>
    fun requestPagingCats(): Flow<PagingData<CatEntity>>

    //room
    suspend fun getSizedCats(size: Int): List<CatEntity>
    suspend fun getCatById(id: String): Result<CatEntity>
    suspend fun getCatByUrl(url: String): Result<CatEntity>
    suspend fun insertCat(cat: CatEntity)
    suspend fun deleteCatById(id: String)
    suspend fun deleteAllCats()
}