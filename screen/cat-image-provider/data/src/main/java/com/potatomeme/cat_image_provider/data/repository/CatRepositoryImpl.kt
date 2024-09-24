package com.potatomeme.cat_image_provider.data.repository


import com.potatomeme.cat_image_provider.data.mapper.CIPDataToDomainMapper.toData
import com.potatomeme.cat_image_provider.data.mapper.CIPDataToDomainMapper.toDomain
import com.potatomeme.cat_image_provider.data.source.local.CatRoomDataSource
import com.potatomeme.cat_image_provider.data.source.remote.CatRetrofitDataSource
import com.potatomeme.cat_image_provider.domain.entity.CatEntity
import com.potatomeme.cat_image_provider.domain.repository.CatRepository
import javax.inject.Inject

class CatRepositoryImpl @Inject constructor(
    private val catRetrofitDataSource: CatRetrofitDataSource,
    private val catRoomDataSource: CatRoomDataSource,
) : CatRepository {
    override suspend fun requestCats(
        limit: Int,
        page: Int,
        order: String,
    ): Result<List<CatEntity>> {
        return try {
            val response = catRetrofitDataSource.requestCats(limit, page, order)
            if (response.isSuccessful) {
                Result.success(response.body()?.map {
                    it.toDomain()
                } ?: emptyList())
            } else {
                throw Exception("Network call failed: ${response.message()}")
            }
        } catch (
            e: Exception,
        ) {
            Result.failure(e)
        }
    }

    override suspend fun getSizedCats(size: Int): List<CatEntity> {
        val cats = catRoomDataSource.getAllCats()
        return cats.map { it.toDomain() }
    }

    override suspend fun getCatById(id: String): Result<CatEntity> {
        val cat = catRoomDataSource.getCatById(id)
        return if (cat != null) {
            Result.success(cat.toDomain())
        } else{
            Result.failure(Exception("Cat not found"))
        }
    }

    override suspend fun getCatByUrl(url: String): Result<CatEntity> {
        val cat = catRoomDataSource.getCatByUrl(url)
        return if (cat != null) {
            Result.success(cat.toDomain())
        } else{
            Result.failure(Exception("Cat not found"))
        }
    }

    override suspend fun insertCat(cat: CatEntity) {
        catRoomDataSource.insertCat(cat.toData())
    }

    override suspend fun deleteCatById(id: String) {
        catRoomDataSource.deleteCatById(id)
    }

    override suspend fun deleteAllCats() {
        catRoomDataSource.deleteAllCats()
    }

}