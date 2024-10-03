package com.potatomeme.jet_news.data.di

import com.potatomeme.jet_news.data.source.InterestsDataSource
import com.potatomeme.jet_news.data.source.PostDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class JetNewsModule {
    @Provides
    @Singleton
    fun providePostDataSource() : PostDataSource = PostDataSource()

    @Provides
    @Singleton
    fun provideInterestsDataSource() : InterestsDataSource = InterestsDataSource()

}