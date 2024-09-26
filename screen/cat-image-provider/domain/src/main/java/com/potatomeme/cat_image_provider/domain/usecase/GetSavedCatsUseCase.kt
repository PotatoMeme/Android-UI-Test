package com.potatomeme.cat_image_provider.domain.usecase

import com.potatomeme.cat_image_provider.domain.repository.CatRepository
import javax.inject.Inject

class GetSavedCatsUseCase @Inject constructor(
    private val catRepository: CatRepository
){
    operator fun invoke() = catRepository.getSavedCats()
}