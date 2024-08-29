package com.potatomeme.android_ui_test.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.potatomeme.android_ui_test.R

@Composable
fun ItemHolder(route: Route.ItemRoute, onItemClick: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(3.dp),
        border = BorderStroke(3.dp, MaterialTheme.colorScheme.secondary),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        onClick = onItemClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 10.dp, 10.dp, 10.dp)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(vertical = 5.dp)
                    .background(MaterialTheme.colorScheme.secondary),
                painter = painterResource(id = route.drawableId),
                contentDescription = route.contentDescription,
                contentScale = ContentScale.Fit
            )
            Text(
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                text = route.route,
                fontSize = 25.sp,
                maxLines = 1,
            )
            Text(
                text = route.contentDescription,
                overflow = TextOverflow.Ellipsis,
                fontSize = 15.sp,
                minLines = 2,
                maxLines = 2,
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ItemHolderPreview() {
    ItemHolder(
        route = Route.ItemRoute(
            "SampleRoute",
            "SampleRoute Description",
            R.drawable.ic_launcher_foreground,
            ItemType.UI_SCREEN
        )
    ) {}
}