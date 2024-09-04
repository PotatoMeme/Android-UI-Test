package com.potatomeme.android_ui_test.main

import androidx.activity.ComponentActivity
import androidx.annotation.DrawableRes
import com.potatomeme.android_ui_test.R

sealed class Route(
    val route: String,
    val contentDescription: String,
) {
    data object MainRoute : Route(
        "MainScreen",
        "메인 화면"
    )

    class ItemRoute(
        route: String,
        contentDescription: String,
        @DrawableRes val drawableId: Int,
        val itemType: ItemType,
        val subItems: Array<Pair<String, Class<out ComponentActivity>>> = arrayOf(),
    ) : Route(
        route = route,
        contentDescription = contentDescription
    )
}

fun sampleRoute(num: Int): Route.ItemRoute = Route.ItemRoute(
    "SampleUI $num",
    "SampleUI Description",
    R.drawable.ic_launcher_foreground,
    if (num % 2 == 1) ItemType.UI_SCREEN else ItemType.LIBRARY,
    arrayOf()
)


enum class ItemType(val str : String){
    LIBRARY("라이브러리 적용"),
    UI_SCREEN("UI 구현"),
    CUSTOM_VIEW("커스텀 뷰 구현"),
}
