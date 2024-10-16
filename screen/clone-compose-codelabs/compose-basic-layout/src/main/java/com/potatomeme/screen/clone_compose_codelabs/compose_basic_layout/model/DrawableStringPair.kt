package com.potatomeme.screen.clone_compose_codelabs.compose_basic_layout.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class DrawableStringPair(
    @DrawableRes val drawable: Int,
    @StringRes val text: Int
)