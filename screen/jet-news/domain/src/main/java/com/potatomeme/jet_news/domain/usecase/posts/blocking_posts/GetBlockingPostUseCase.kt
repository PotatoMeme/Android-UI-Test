package com.potatomeme.jet_news.domain.usecase.posts.blocking_posts

import com.potatomeme.jet_news.domain.anotation.BlockingPosts
import com.potatomeme.jet_news.domain.entity.Post
import com.potatomeme.jet_news.domain.entity.Result
import com.potatomeme.jet_news.domain.repository.PostsRepository
import javax.inject.Inject

class GetBlockingPostUseCase @Inject constructor(
    @BlockingPosts
    private val postsRepository: PostsRepository,
) {
    suspend operator fun invoke(postId: String): Result<Post> {
        return postsRepository.getPost(postId)
    }
}