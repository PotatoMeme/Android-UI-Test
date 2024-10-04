package com.potatomeme.jet_news.domain.usecase.posts.fake_posts

import com.potatomeme.jet_news.domain.anotation.FakePosts
import com.potatomeme.jet_news.domain.repository.PostsRepository
import javax.inject.Inject

class ToggleFakeFavoriteUseCase @Inject constructor(
    @FakePosts
    private val postsRepository: PostsRepository,
){
    suspend operator fun invoke(postId: String) {
        postsRepository.toggleFavorite(postId)
    }
}