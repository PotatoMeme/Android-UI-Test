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

import com.potatomeme.jet_news.data.source.InterestsDataSource
import com.potatomeme.jet_news.domain.entity.InterestSection
import com.potatomeme.jet_news.domain.entity.TopicSelection
import com.potatomeme.jet_news.domain.entity.Result
import com.potatomeme.jet_news.domain.repository.InterestsRepository
import com.potatomeme.jet_news.data.util.addOrRemove
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


/**
 * Implementation of InterestRepository that returns a hardcoded list of
 * topics, people and publications synchronously.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class FakeInterestsRepository @Inject constructor(
    private val interestsDataSource: InterestsDataSource,
) : InterestsRepository {
    // for now, keep the selections in memory
    private val selectedTopics = MutableStateFlow(setOf<TopicSelection>())
    private val selectedPeople = MutableStateFlow(setOf<String>())
    private val selectedPublications = MutableStateFlow(setOf<String>())

    override suspend fun getTopics(): Result<List<InterestSection>> {
        return Result.Success(interestsDataSource.topics)
    }

    override suspend fun getPeople(): Result<List<String>> {
        return Result.Success(interestsDataSource.people)
    }

    override suspend fun getPublications(): Result<List<String>> {
        return Result.Success(interestsDataSource.publications)
    }

    override suspend fun toggleTopicSelection(topic: TopicSelection) {
        selectedTopics.update {
            it.addOrRemove(topic)
        }
    }

    override suspend fun togglePersonSelected(person: String) {
        selectedPeople.update {
            it.addOrRemove(person)
        }
    }

    override suspend fun togglePublicationSelected(publication: String) {
        selectedPublications.update {
            it.addOrRemove(publication)
        }
    }

    override fun observeTopicsSelected(): Flow<Set<TopicSelection>> = selectedTopics

    override fun observePeopleSelected(): Flow<Set<String>> = selectedPeople

    override fun observePublicationSelected(): Flow<Set<String>> = selectedPublications
}
