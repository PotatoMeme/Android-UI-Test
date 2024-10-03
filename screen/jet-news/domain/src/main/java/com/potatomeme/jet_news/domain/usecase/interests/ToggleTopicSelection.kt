package com.potatomeme.jet_news.domain.usecase.interests

import com.potatomeme.jet_news.domain.entity.TopicSelection
import com.potatomeme.jet_news.domain.repository.InterestsRepository
import javax.inject.Inject

class ToggleTopicSelection @Inject constructor(
    private val interestsRepository: InterestsRepository,
) {
    suspend operator fun invoke(topic: TopicSelection) {
        interestsRepository.toggleTopicSelection(topic)
    }
}