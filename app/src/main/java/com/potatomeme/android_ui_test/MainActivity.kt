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
import com.potatomeme.cat_image_provider.presentation.on_boarding.CIPOnBoardingActivity
import com.potatomeme.chirang_note_app.presentation_xml.on_boarding.CNAOnBoardingActivity
import com.potatomeme.jet_news.presentation.ui.JNMainActivity
import com.potatomeme.screen.planfit.presentation.on_board.PlanfitOnBoardingActivity
import com.potatomeme.ticket_booking_app.presentation.ui.on_boarding.TBAOnBoardingActivity

class MainActivity : ComponentActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    private val customViewList = listOf(
        Route.ItemRoute(
            route = "CustomView Sample",
            contentDescription = "CustomView : Draw Canvas description",
            drawableId = R.drawable.drawcanvas,
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
            contentDescription = "CustomView : Chart description",
            drawableId = R.drawable.chart,
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
        )
    )

    private val screenList = listOf(
        Route.ItemRoute(
            route = "Planfit CloneCoding",
            contentDescription = "Planfit : CloneCoding description",
            drawableId = R.drawable.planfit,
            itemType = ItemType.UI_SCREEN,
            subItems = arrayOf(
                Pair(
                    "xml"
                ) {
                    startActivity(
                        Intent(
                            this,
                            PlanfitOnBoardingActivity::class.java
                        )
                    )
                }
            )
        ),
        Route.ItemRoute(
            route = "TBA Ticket Booking App CloneCoding",
            contentDescription = "TBA : Ticket Booking App CloneCoding",
            drawableId = R.drawable.tba,
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
            route = "CIP Cat Image Provider",
            contentDescription = "CIP : Cat Image Provider develop",
            drawableId = R.drawable.cip,
            itemType = ItemType.UI_SCREEN,
            subItems = arrayOf(
                Pair(
                    "xml"
                ) {
                    startActivity(
                        Intent(
                            this,
                            CIPOnBoardingActivity::class.java
                        )
                    )
                }
            )
        ),
        Route.ItemRoute(
            route = "CNA Chirang Note App",
            contentDescription = "CNA :  Chirang Note App Refactoring",
            drawableId = R.drawable.cna_logo,
            itemType = ItemType.UI_SCREEN,
            subItems = arrayOf(
                Pair(
                    "xml"
                ) {
                    startActivity(
                        Intent(
                            this,
                            CNAOnBoardingActivity::class.java
                        )
                    )
                }
            )
        ),
        Route.ItemRoute(
            route = "JN Jetnews App CloneCoding",
            contentDescription = "JN : Jetnews App CloneCoding",
            drawableId = R.drawable.ic_launcher_foreground,
            itemType = ItemType.UI_SCREEN,
            subItems = arrayOf(
                Pair(
                    "compose"
                ) {
                    startActivity(
                        Intent(
                            this,
                            JNMainActivity::class.java
                        )
                    )
                }
            )
        ),
    )


    private val routeList: List<Route.ItemRoute> = customViewList + screenList

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