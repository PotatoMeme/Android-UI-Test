package com.potatomeme.android_ui_test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.potatomeme.android_ui_test.main.ItemType
import com.potatomeme.android_ui_test.main.MainNavHost
import com.potatomeme.android_ui_test.main.Route
import com.potatomeme.android_ui_test.ui.theme.AndroidUITestTheme

class MainActivity : ComponentActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }


    private val routeList: List<Route.ItemRoute> = listOf(
        /*sampleRoute(0),
        sampleRoute(1),
        sampleRoute(2),
        sampleRoute(3),
        sampleRoute(4),
        sampleRoute(5),
        sampleRoute(6),*/
        Route.ItemRoute(
            route = "CustomView Sample",
            contentDescription = "CustomView Sample description",
            drawableId = R.drawable.ic_launcher_foreground,//나중에 스크린샷으로 변경
            itemType = ItemType.CUSTOM_VIEW,
            subItems = arrayOf(
                Pair(
                    "xml",
                    com.potatomeme.custom_ui.sample.MainActivity::class.java
                )
            )
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidUITestTheme {
                MainNavHost(
                    routeList
                )
            }
        }
    }
}