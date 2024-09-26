package com.potatomeme.cat_image_provider.data.dao

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.potatomeme.cat_image_provider.data.model.Cat
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {
    //select
    @Query("SELECT * FROM cat")
    suspend fun getAllCats(): List<Cat>

    @Query("SELECT * FROM cat")
    fun getPagingCats(): PagingSource<Int,Cat>

    @Query("SELECT * FROM cat WHERE id = :id")
    suspend fun getCatById(id: String): Cat?

    @Query("SELECT * FROM cat WHERE url = :url")
    suspend fun getCatByUrl(url: String): Cat?

    //insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCat(cat: Cat)

    //delete
    @Query("DELETE FROM cat WHERE id = :id")
    suspend fun deleteCatById(id: String)

    @Query("DELETE FROM cat")
    suspend fun deleteAllCats()
}