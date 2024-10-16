package com.potatomeme.screen.clone_compose_codelabs.compose_theming.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.potatomeme.screen.clone_compose_codelabs.compose_theming.data.LocalEmailsDataProvider
import com.potatomeme.screen.clone_compose_codelabs.compose_theming.ui.theme.AndroidUITestTheme

class CTMainActivity : ComponentActivity() {
    private val viewModel: ReplyHomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            AndroidUITestTheme {
                Surface(tonalElevation = 5.dp) {
                    ReplyApp(
                        replyHomeUIState = uiState,
                        closeDetailScreen = {
                            viewModel.closeDetailScreen()
                        },
                        navigateToDetail = { emailId ->
                            viewModel.setSelectedEmail(emailId)
                        }
                    )
                }
            }
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark when sdk >= 31",
    apiLevel = 31,
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight when sdk > 31",
    apiLevel = 31,
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
    apiLevel = 28,
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
    apiLevel = 28,
)
@Composable
fun ReplyAppPreview() {
    AndroidUITestTheme {
        ReplyApp(
            replyHomeUIState = ReplyHomeUIState(
                emails = LocalEmailsDataProvider.allEmails
            )
        )
    }
}