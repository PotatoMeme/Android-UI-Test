package com.potatomeme.jet_news.domain.usecase.interests

import com.potatomeme.jet_news.domain.entity.Result
import com.potatomeme.jet_news.domain.repository.InterestsRepository
import javax.inject.Inject

class GetPeopleUseCase @Inject constructor(
    private val interestsRepository: InterestsRepository,
) {
    suspend operator fun invoke(): Result<List<String>> {
        return interestsRepository.getPeople()
    }
}