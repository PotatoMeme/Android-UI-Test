package com.potatomeme.android_ui_test.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun MainNavHost(
    itemList: List<Route.ItemRoute>,
) {
    val navController: NavHostController = rememberNavController()

    Surface {
        Scaffold(
            /*topBar = {
                TopAppBar(
                    modifier = Modifier.background(Purple40),
                    title = {
                        Text(text = "Android-UI-Test")
                    },
                )
            },*/
            content = { paddingValues: PaddingValues ->
                NavHost(
                    modifier = Modifier.padding(paddingValues),
                    navController = navController,
                    startDestination = Route.MainRoute.route
                ) {
                    composable(route = Route.MainRoute.route) {
                        MainScreen(list = itemList) { str ->
                            navController.navigate(str)
                        }
                    }
                    itemList.forEach { itemRoute ->
                        composable(itemRoute.route) {

                        }
                    }
                }
            }
        )
    }
}


