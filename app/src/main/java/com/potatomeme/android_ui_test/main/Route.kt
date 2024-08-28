package com.potatomeme.android_ui_test.main

import androidx.activity.ComponentActivity
import androidx.annotation.DrawableRes

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
        val subItems: Array<Pair<String, Class<out ComponentActivity>>> = arrayOf(),
    ) : Route(
        route = route,
        contentDescription = contentDescription
    )
}
