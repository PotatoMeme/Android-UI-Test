/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * This code has been modified by KimSeongHwan in 2024.
 * Changes: modified to current environment
 */
package com.potatomeme.jet_news.presentation.ui

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.example.jetnews.ui.home.HomeRoute
import com.potatomeme.jet_news.presentation.di.ViewModelFactoryProvider
import com.potatomeme.jet_news.presentation.ui.home.HomeViewModel
import com.potatomeme.jet_news.presentation.util.JetnewsObj.JETNEWS_APP_URI
import dagger.hilt.EntryPoint
import dagger.hilt.android.EntryPointAccessors

const val POST_ID = "postId"

@Composable
fun JetnewsNavGraph(
    isExpandedScreen: Boolean,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    openDrawer: () -> Unit = {},
    startDestination: String = JetnewsDestinations.HOME_ROUTE,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            route = JetnewsDestinations.HOME_ROUTE,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern =
                        "$JETNEWS_APP_URI/${JetnewsDestinations.HOME_ROUTE}?$POST_ID={$POST_ID}"
                }
            )
        ) { navBackStackEntry ->
            //todo home route
            val factory = EntryPointAccessors.fromActivity(
                LocalContext.current as Activity,
                ViewModelFactoryProvider::class.java
            ).homeViewModelFactory()
            val viewModel: HomeViewModel = viewModel(
                factory = HomeViewModel.provideFactory(
                    factory,
                    navBackStackEntry.arguments?.getString(POST_ID)
                )
            )
            HomeRoute(
                viewModel,
                isExpandedScreen,
                openDrawer
            )
        }
        composable(JetnewsDestinations.INTERESTS_ROUTE) {
            //todo interests route
        }
    }
}