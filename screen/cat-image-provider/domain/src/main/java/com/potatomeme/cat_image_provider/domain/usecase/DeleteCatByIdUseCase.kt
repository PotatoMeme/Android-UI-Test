package com.potatomeme.cat_image_provider.domain.usecase

import com.potatomeme.cat_image_provider.domain.repository.CatRepository
import javax.inject.Inject

class DeleteCatByIdUseCase @Inject constructor(
    private val catRepository: CatRepository
){
    suspend operator fun invoke(id: String) = catRepository.deleteCatById(id)
}