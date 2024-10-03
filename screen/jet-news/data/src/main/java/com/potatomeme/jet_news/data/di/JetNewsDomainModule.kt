package com.potatomeme.jet_news.data.di

import com.potatomeme.jet_news.data.repository.BlockingFakePostsRepository
import com.potatomeme.jet_news.data.repository.FakeInterestsRepository
import com.potatomeme.jet_news.data.repository.FakePostsRepository
import com.potatomeme.jet_news.domain.repository.InterestsRepository
import com.potatomeme.jet_news.domain.repository.PostsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class JetNewsDomainModule {
    @Binds
    abstract fun bindInterestsRepository(repository: FakeInterestsRepository): InterestsRepository

    @Binds
    abstract fun bindPostsRepository(repository: FakePostsRepository): PostsRepository

    @Binds
    @BlockingPosts
    abstract fun bindBlockingPostsRepository(repository: BlockingFakePostsRepository): PostsRepository
}
