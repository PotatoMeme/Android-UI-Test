package com.potatomeme.cat_image_provider.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.potatomeme.cat_image_provider.data.dao.CatDao
import com.potatomeme.cat_image_provider.data.model.Cat

@Database(entities = [Cat::class],version = 1, exportSchema = false)
abstract class CatDB : RoomDatabase() {
    abstract fun catDao(): CatDao
}