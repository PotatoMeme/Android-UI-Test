package com.potatomeme.cat_image_provider.data.di

import com.potatomeme.cat_image_provider.data.repository.CatRepositoryImpl
import com.potatomeme.cat_image_provider.domain.repository.CatRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CatModule {
    @Binds
    abstract fun bindCatRepository(repository: CatRepositoryImpl): CatRepository
}