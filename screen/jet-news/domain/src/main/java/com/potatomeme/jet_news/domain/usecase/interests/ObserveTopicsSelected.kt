package com.potatomeme.jet_news.domain.usecase.interests

import com.potatomeme.jet_news.domain.entity.TopicSelection
import com.potatomeme.jet_news.domain.repository.InterestsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveTopicsSelected @Inject constructor(
    private val interestsRepository: InterestsRepository,
) {
    operator fun invoke(): Flow<Set<TopicSelection>> {
        return interestsRepository.observeTopicsSelected()
    }
}