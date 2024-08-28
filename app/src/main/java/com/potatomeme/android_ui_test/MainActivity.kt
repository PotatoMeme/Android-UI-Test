package com.potatomeme.android_ui_test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.potatomeme.android_ui_test.main.MainNavHost
import com.potatomeme.android_ui_test.main.Route
import com.potatomeme.android_ui_test.ui.theme.AndroidUITestTheme

class MainActivity : ComponentActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }


    private val routeList: List<Route.ItemRoute> = listOf(
        sampleRoute(),
    )

    private fun sampleRoute(): Route.ItemRoute = Route.ItemRoute(
        "SampleUI",
        "SampleUI Description",
        R.drawable.ic_launcher_foreground,
        arrayOf()
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
