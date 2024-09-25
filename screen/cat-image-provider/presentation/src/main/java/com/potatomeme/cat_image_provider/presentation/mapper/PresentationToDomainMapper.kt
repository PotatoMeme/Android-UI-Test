package com.potatomeme.cat_image_provider.presentation.mapper

import com.potatomeme.cat_image_provider.domain.entity.CatEntity
import com.potatomeme.cat_image_provider.presentation.model.ParcelableCat

object PresentationToDomainMapper {
    fun ParcelableCat.toDomain(): CatEntity = CatEntity(
        id = id,
        url = url,
        width = width,
        height = height
    )

    fun CatEntity.toParcelable(): ParcelableCat = ParcelableCat(
        id = id,
        url = url,
        width = width,
        height = height
    )
}