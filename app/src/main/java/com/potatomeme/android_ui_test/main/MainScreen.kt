package com.potatomeme.android_ui_test.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen(list: List<Route.ItemRoute>, onItemClick: (String) -> Unit) {
    val scrollState = rememberScrollState()

    var isShow = remember {
        mutableStateMapOf<ItemType, Boolean>()
    }

    LaunchedEffect(key1 = Unit) {
        isShow = isShow.apply {
            put(ItemType.LIBRARY, true)
            put(ItemType.UI_SCREEN, true)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
        //.verticalScroll(scrollState)
    ) {
        val listGroupByType = list.groupBy { it.itemType }

        listGroupByType.forEach { (itemType, itemRoutes) ->
            item {
                Card(
                    elevation = CardDefaults.cardElevation(3.dp),
                    border = BorderStroke(3.dp, MaterialTheme.colorScheme.secondary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .toggleable(
                                value = isShow[itemType] == true,
                                onValueChange = {
                                    isShow[itemType] = it
                                },
                                //role = Role.Button
                            )
                    ) {
                        Row(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = itemType.str,
                                fontWeight = FontWeight.Bold,
                                fontSize = 25.sp,
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "(${itemRoutes.size})",
                                fontWeight = FontWeight.Bold,
                                fontSize = 25.sp,
                                modifier = Modifier.align(CenterVertically)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Icon(
                            if (isShow[itemType] == true) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                            contentDescription = null,
                            modifier = Modifier.align(CenterVertically) // 아이콘을 세로 중앙에 맞춤
                        )
                    }
                }
            }
            if (isShow[itemType] == true) {
                items(itemRoutes, key = { it.route }) { item ->
                    ItemHolder(route = item) {
                        onItemClick(item.route)
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MainScreenPreview() {
    MainScreen(
        list = listOf(
            sampleRoute(0),
            sampleRoute(1),
            sampleRoute(2),
            sampleRoute(3),
            sampleRoute(4),
            sampleRoute(5),
            sampleRoute(6),
        )
    ) {}
}