package com.potatomeme.cat_image_provider.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.potatomeme.cat_image_provider.data.mapper.CIPDataToDomainMapper.toDomain
import com.potatomeme.cat_image_provider.data.source.remote.CatRetrofitDataSource
import com.potatomeme.cat_image_provider.domain.entity.CatEntity
import javax.inject.Inject

class CatRequestPagingSource @Inject constructor(
    private val dataSource: CatRetrofitDataSource,
) : PagingSource<Int, CatEntity>() {
    override fun getRefreshKey(state: PagingState<Int, CatEntity>): Int? {
        return state.anchorPosition?.let { anchorPos ->
            state.closestPageToPosition(anchorPos)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPos)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatEntity> {
        Log.d("TAG", "load")
        return try {
            val pageNumber = params.key ?: 0
            val response = dataSource.requestCats(10, pageNumber, "ASC")
            val endOfPaginationReached = response.body()?.size!! < 10
            val data = response.body()?.map {
                it.toDomain()
            } ?: emptyList()

            Log.d("TAG", "load: $pageNumber")

            val prevKey = if (pageNumber == 0) null else pageNumber - 1
            val nextKey = if (endOfPaginationReached) null else pageNumber + 1

            LoadResult.Page(
                data = data,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}