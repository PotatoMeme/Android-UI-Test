package com.potatomeme.cat_image_provider.data.di

import android.app.Application
import androidx.room.Room
import com.potatomeme.cat_image_provider.data.dao.CatDao
import com.potatomeme.cat_image_provider.data.database.CatDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Provides
    @Singleton
    fun provideCatDB(application: Application): CatDB = Room.databaseBuilder(
        application, CatDB::class.java, "cat_db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideCatDao(catDB: CatDB): CatDao = catDB.catDao()

}