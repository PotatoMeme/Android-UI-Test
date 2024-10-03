package com.potatomeme.jet_news.domain.usecase.posts.blocking_posts

import com.potatomeme.jet_news.domain.anotation.BlockingPosts
import com.potatomeme.jet_news.domain.entity.PostsFeed
import com.potatomeme.jet_news.domain.repository.PostsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveBlockingPostsFeedUseCase @Inject constructor(
    @BlockingPosts
    private val postsRepository: PostsRepository,
) {
    operator fun invoke(): Flow<PostsFeed?> {
        return postsRepository.observePostsFeed()
    }
}