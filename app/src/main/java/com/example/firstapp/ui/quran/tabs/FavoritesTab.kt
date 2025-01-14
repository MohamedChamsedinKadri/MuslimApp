package com.example.firstapp.ui.quran.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FavoritesTab() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Favorites", modifier = Modifier.padding(16.dp))
        // Add your favorite verses here
    }
}