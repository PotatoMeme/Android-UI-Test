package com.potatomeme.cat_image_provider.data.mapper

import com.potatomeme.cat_image_provider.data.model.Cat
import com.potatomeme.cat_image_provider.domain.entity.CatEntity

object CIPDataToDomainMapper {
    fun Cat.toDomain(): CatEntity {
        return CatEntity(
            height = height,
            id = id,
            url = url,
            width = width
        )
    }

    fun CatEntity.toData(): Cat {
        return Cat(
            height = height,
            id = id,
            url = url,
            width = width
        )
    }
}