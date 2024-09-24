package com.potatomeme.cat_image_provider.domain.usecase

import com.potatomeme.cat_image_provider.domain.repository.CatRepository
import javax.inject.Inject

class RequestCatsUseCase @Inject constructor(
    private val catRepository: CatRepository,
) {
    suspend operator fun invoke(limit: Int, page: Int, order: String) =
        catRepository.requestCats(limit, page, order)
}