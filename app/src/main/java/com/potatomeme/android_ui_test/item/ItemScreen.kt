package com.potatomeme.android_ui_test.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.potatomeme.android_ui_test.R
import com.potatomeme.android_ui_test.main.ItemType
import com.potatomeme.android_ui_test.main.Route

@Composable
fun ItemScreen(itemRoute: Route.ItemRoute) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black),
            contentScale = ContentScale.FillWidth,
            painter = painterResource(id = itemRoute.drawableId),
            contentDescription = itemRoute.contentDescription
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            modifier = Modifier.padding(10.dp),
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            text = itemRoute.route,
            fontSize = 25.sp,
        )
        Text(
            modifier = Modifier.padding(10.dp),
            text = itemRoute.contentDescription,
            overflow = TextOverflow.Ellipsis,
            fontSize = 15.sp,
        )
        itemRoute.subItems.forEach { (str, action) ->
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 3.dp),
                onClick = {
                    action()
                }) {
                Text(text = str)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
@Preview(showBackground = true)
fun ItemScreenPreview() {
    ItemScreen(
        Route.ItemRoute(
            "Sample Item",
            "Sample Item Description",
            R.drawable.ic_launcher_foreground,
            ItemType.LIBRARY,
            arrayOf(
                Pair("Sample1"){},
                Pair("Sample2"){},
                Pair("Sample3"){},
                Pair("Sample4"){},
                Pair("Sample5"){},
            )
        )
    )
}


