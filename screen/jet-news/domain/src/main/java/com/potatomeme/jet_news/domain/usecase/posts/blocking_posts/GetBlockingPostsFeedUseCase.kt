package com.potatomeme.jet_news.domain.usecase.posts.blocking_posts

import com.potatomeme.jet_news.domain.anotation.BlockingPosts
import com.potatomeme.jet_news.domain.entity.PostsFeed
import com.potatomeme.jet_news.domain.entity.Result
import com.potatomeme.jet_news.domain.repository.PostsRepository
import javax.inject.Inject

class GetBlockingPostsFeedUseCase @Inject constructor(
    @BlockingPosts
    private val postsRepository: PostsRepository,
) {
    suspend operator fun invoke(): Result<PostsFeed> {
        return postsRepository.getPostsFeed()
    }
}