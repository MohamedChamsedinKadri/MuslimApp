package com.example.firstapp

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

class WebViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val url = intent.getStringExtra("url") ?: "https://quran411.com" // Fallback to base URL

        // Set up WebView inside a ComposeView
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.White
            ) {
                WebViewScreen(url = url)
            }
        }
    }
}

@Composable
fun WebViewScreen(url: String) {
    val context = LocalContext.current
    AndroidView(factory = {
        WebView(context).apply {
            webViewClient = WebViewClient() // Keeps navigation inside the WebView
            loadUrl(url)
        }
    }, modifier = Modifier.fillMaxSize())
}
