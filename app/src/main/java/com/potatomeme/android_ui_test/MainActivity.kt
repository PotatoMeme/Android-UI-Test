package com.potatomeme.android_ui_test

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.potatomeme.android_ui_test.main.ItemType
import com.potatomeme.android_ui_test.main.MainNavHost
import com.potatomeme.android_ui_test.main.Route
import com.potatomeme.android_ui_test.ui.theme.AndroidUITestTheme
import com.potatomeme.ticket_booking_app.presentation.ui.on_boarding.TBAOnBoardingActivity

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
                    "xml"
                ) {
                    Log.d(TAG, "CustomView Sample")
                    startActivity(
                        Intent(
                            this,
                            com.potatomeme.custom_ui.sample.MainActivity::class.java
                        )
                    )
                }
            )
        ),
        Route.ItemRoute(
            route = "CustomView Chart",
            contentDescription = "CustomView Chart description",
            drawableId = R.drawable.ic_launcher_foreground,//나중에 스크린샷으로 변경
            itemType = ItemType.CUSTOM_VIEW,
            subItems = arrayOf(
                Pair(
                    "xml"
                ) {
                    Log.d(TAG, "CustomView Chart")
                    startActivity(
                        Intent(
                            this,
                            com.potatomeme.custom_ui.chart.CustomViewChartActivity::class.java
                        )
                    )
                }
            )
        ),
        Route.ItemRoute(
            route = "Planfit CloneCoding",
            contentDescription = "Planfit CloneCoding description",
            drawableId = R.drawable.ic_launcher_foreground,//나중에 스크린샷으로 변경
            itemType = ItemType.UI_SCREEN,
            subItems = arrayOf(
                Pair(
                    "xml"
                ) {
                    startActivity(
                        Intent(
                            this,
                            TBAOnBoardingActivity::class.java
                        )
                    )
                }
            )
        ),
        Route.ItemRoute(
            route = "TBA Ticket Booking App CloneCoding",
            contentDescription = "TBA Ticket Booking App CloneCoding",
            drawableId = R.drawable.ic_launcher_foreground,//나중에 스크린샷으로 변경
            itemType = ItemType.UI_SCREEN,
            subItems = arrayOf(
                Pair(
                    "xml"
                ) {
                    startActivity(
                        Intent(
                            this,
                            TBAOnBoardingActivity::class.java
                        )
                    )
                }
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