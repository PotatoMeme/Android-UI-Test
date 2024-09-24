package com.potatomeme.cat_image_provider.domain.usecase

import com.potatomeme.cat_image_provider.domain.repository.CatRepository

class GetCatByIdUseCase(
    private val catRepository: CatRepository
){
    suspend operator fun invoke(id: String) = catRepository.getCatById(id)
}