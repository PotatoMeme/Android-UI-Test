package com.potatomeme.cat_image_provider.domain.usecase

import com.potatomeme.cat_image_provider.domain.entity.CatEntity
import com.potatomeme.cat_image_provider.domain.repository.CatRepository

class InsertCatUseCase(
    private val catRepository: CatRepository
){
    suspend operator fun invoke(cat: CatEntity) = catRepository.insertCat(cat)
}
