package com.potatomeme.cat_image_provider.data.source.local

import com.potatomeme.cat_image_provider.data.dao.CatDao
import com.potatomeme.cat_image_provider.data.model.Cat
import javax.inject.Inject

class CatRoomDataSource @Inject constructor(private val catDao: CatDao) {
    //select == get
    suspend fun getAllCats() = catDao.getAllCats()
    suspend fun getCatById(id: String) = catDao.getCatById(id)
    suspend fun getCatByUrl(url: String) = catDao.getCatByUrl(url)
    //insert
    suspend fun insertCat(cat: Cat) = catDao.insertCat(cat)
    //delete
    suspend fun deleteCatById(id: String) = catDao.deleteCatById(id)
    suspend fun deleteAllCats() = catDao.deleteAllCats()

}