/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * This code has been modified by KimSeongHwan in 2024.
 * Changes: Split files,use hilt
 */
package com.potatomeme.jet_news.data.repository

import com.potatomeme.jet_news.data.source.PostDataSource
import com.potatomeme.jet_news.data.util.addOrRemove
import com.potatomeme.jet_news.domain.entity.Post
import com.potatomeme.jet_news.domain.entity.Result
import com.potatomeme.jet_news.domain.entity.PostsFeed
import com.potatomeme.jet_news.domain.repository.PostsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FakePostsRepository @Inject constructor(
    private val postDataSource: PostDataSource,
) : PostsRepository {

    // for now, store these in memory
    private val favorites = MutableStateFlow<Set<String>>(setOf())

    private val postsFeed = MutableStateFlow<PostsFeed?>(null)

    // Used to make suspend functions that read and update state safe to call from any thread

    override suspend fun getPost(postId: String?): Result<Post> {
        return withContext(Dispatchers.IO) {
            val post = postDataSource.posts.allPosts.find { it.id == postId }
            if (post == null) {
                Result.Error(IllegalArgumentException("Post not found"))
            } else {
                Result.Success(post)
            }
        }
    }

    override suspend fun getPostsFeed(): Result<PostsFeed> {
        return withContext(Dispatchers.IO) {
            delay(800) // pretend we're on a slow network
            if (shouldRandomlyFail()) {
                Result.Error(IllegalStateException())
            } else {
                postsFeed.update { postDataSource.posts }
                Result.Success(postDataSource.posts)
            }
        }
    }

    override fun observeFavorites(): Flow<Set<String>> = favorites
    override fun observePostsFeed(): Flow<PostsFeed?> = postsFeed

    override suspend fun toggleFavorite(postId: String) {
        favorites.update {
            it.addOrRemove(postId)
        }
    }

    // used to drive "random" failure in a predictable pattern, making the first request always
    // succeed
    private var requestCount = 0

    /**
     * Randomly fail some loads to simulate a real network.
     *
     * This will fail deterministically every 5 requests
     */
    private fun shouldRandomlyFail(): Boolean = ++requestCount % 5 == 0
}
