package com.example.firstapp.ui.quran.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.firstapp.ui.quran.components.JuzCard

@Composable
fun JuzTab() {
    val juzList = listOf(
        "Juz 1",
        "Juz 2",
        "Juz 3",
        "Juz 4",
        "Juz 5"
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Juz List", modifier = Modifier.padding(16.dp))
        LazyColumn {
            itemsIndexed(juzList) { index, juzName ->
                JuzCard(juzNumber = index + 1, juzName = juzName)
            }
        }
    }
}