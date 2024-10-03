package com.potatomeme.jet_news.domain.usecase.interests

import com.potatomeme.jet_news.domain.repository.InterestsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObservePublicationSelected @Inject constructor(
    private val interestsRepository: InterestsRepository,
) {
    operator fun invoke(): Flow<Set<String>> {
        return interestsRepository.observePublicationSelected()
    }
}