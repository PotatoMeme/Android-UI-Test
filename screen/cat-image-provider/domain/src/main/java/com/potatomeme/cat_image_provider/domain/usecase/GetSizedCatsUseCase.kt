package com.potatomeme.cat_image_provider.domain.usecase

import com.potatomeme.cat_image_provider.domain.repository.CatRepository
import javax.inject.Inject

class GetSizedCatsUseCase @Inject constructor(
    private val catRepository: CatRepository,
) {
    suspend operator fun invoke(size: Int) = catRepository.getSizedCats(size)
}