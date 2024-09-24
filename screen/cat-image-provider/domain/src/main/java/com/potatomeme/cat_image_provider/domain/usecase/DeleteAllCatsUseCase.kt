package com.potatomeme.cat_image_provider.domain.usecase

import com.potatomeme.cat_image_provider.domain.repository.CatRepository

class DeleteAllCatsUseCase(
    private val catRepository: CatRepository,
) {
    suspend operator fun invoke() = catRepository.deleteAllCats()
}