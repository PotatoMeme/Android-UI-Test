package com.potatomeme.jet_news.domain.usecase.posts.fake_posts

import com.potatomeme.jet_news.domain.anotation.FakePosts
import com.potatomeme.jet_news.domain.repository.PostsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveFakeFavoritesUseCase @Inject constructor(
    @FakePosts
    private val postsRepository: PostsRepository,
) {
    operator fun invoke(): Flow<Set<String>> {
        return postsRepository.observeFavorites()
    }
}