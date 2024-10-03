/*
 * This code has been modified by KimSeongHwan in 2024.
 * Changes: Split files,use hilt
 */
/*
 * This code has been modified by KimSeongHwan in 2024.
 * Changes: Split files,use hilt
 */
package com.potatomeme.jet_news.data.repository

import com.potatomeme.jet_news.data.source.PostDataSource
import com.potatomeme.jet_news.domain.entity.Post
import com.potatomeme.jet_news.domain.entity.PostsFeed
import com.potatomeme.jet_news.domain.entity.Result
import com.potatomeme.jet_news.domain.repository.PostsRepository
import com.potatomeme.jet_news.data.util.addOrRemove
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of PostsRepository that returns a hardcoded list of
 * posts with resources synchronously.
 */

@OptIn(ExperimentalCoroutinesApi::class)
class BlockingFakePostsRepository @Inject constructor(
    private val postDataSource: PostDataSource,
) : PostsRepository {

    // for now, keep the favorites in memory
    private val favorites = MutableStateFlow<Set<String>>(setOf())

    private val postsFeed = MutableStateFlow<PostsFeed?>(null)

    override suspend fun getPost(postId: String?): Result<Post> {
        return withContext(Dispatchers.IO) {
            val post = postDataSource.posts.allPosts.find { it.id == postId }
            if (post == null) {
                Result.Error(IllegalArgumentException("Unable to find post"))
            } else {
                Result.Success(post)
            }
        }
    }

    override suspend fun getPostsFeed(): Result<PostsFeed> {
        postsFeed.update { postDataSource.posts }
        return Result.Success(postDataSource.posts)
    }

    override fun observeFavorites(): Flow<Set<String>> = favorites
    override fun observePostsFeed(): Flow<PostsFeed?> = postsFeed

    override suspend fun toggleFavorite(postId: String) {
        favorites.update { it.addOrRemove(postId) }
    }
}