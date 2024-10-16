package com.potatomeme.screen.clone_compose_codelabs.compose_basic_layout.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.potatomeme.screen.clone_compose_codelabs.compose_basic_layout.ui.home.HomeScreen
import com.potatomeme.screen.clone_compose_codelabs.compose_basic_layout.ui.theme.AndroidUITestTheme

@Composable
fun MySootheApp(windowSize: WindowSizeClass) {
    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {//일반
            MySootheAppPortrait()
        }

        WindowWidthSizeClass.Expanded -> {//확장된
            MySootheAppLandscape()
        }
    }
}

@Composable
fun MySootheAppPortrait() {
    AndroidUITestTheme {
        Scaffold(
            bottomBar = { SootheBottomNavigation() }
        ) { padding ->
            HomeScreen(Modifier.padding(padding))
        }
    }
}

@Composable
fun MySootheAppLandscape() {
    AndroidUITestTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Row {
                SootheNavigationRail()
                HomeScreen()
            }
        }
    }
}
