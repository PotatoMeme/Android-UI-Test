package com.potatomeme.cat_image_provider.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Cat(
    @SerializedName("height")
    val height: Int,
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
)