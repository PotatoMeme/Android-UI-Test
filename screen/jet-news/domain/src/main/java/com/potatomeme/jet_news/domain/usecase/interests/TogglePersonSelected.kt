package com.potatomeme.jet_news.domain.usecase.interests

import com.potatomeme.jet_news.domain.repository.InterestsRepository
import javax.inject.Inject

class TogglePersonSelected @Inject constructor(
    private val interestsRepository: InterestsRepository,
) {
    suspend operator fun invoke(person: String) {
        interestsRepository.togglePersonSelected(person)
    }
}