package com.potatomeme.android_ui_test

import android.content.ClipData.Item
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.potatomeme.android_ui_test.main.ItemType
import com.potatomeme.android_ui_test.main.MainNavHost
import com.potatomeme.android_ui_test.main.Route
import com.potatomeme.android_ui_test.main.sampleRoute
import com.potatomeme.android_ui_test.ui.theme.AndroidUITestTheme

class MainActivity : ComponentActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }


    private val routeList: List<Route.ItemRoute> = listOf(
        sampleRoute(0),
        sampleRoute(1),
        sampleRoute(2),
        sampleRoute(3),
        sampleRoute(4),
        sampleRoute(5),
        sampleRoute(6),
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