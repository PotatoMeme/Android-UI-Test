package com.potatomeme.jet_news.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.potatomeme.jet_news.presentation.ui.theme.JetnewsTheme


class JNMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetnewsTheme() {

            }
        }
    }
}