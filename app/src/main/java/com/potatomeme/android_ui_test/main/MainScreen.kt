package com.potatomeme.android_ui_test.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainScreen(list: List<Route.ItemRoute>, onItemClick: (String) -> Unit) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        list.forEach { itemRoute ->
            ItemHolder(itemRoute) {
                onItemClick(itemRoute.route)
            }
        }
    }
}