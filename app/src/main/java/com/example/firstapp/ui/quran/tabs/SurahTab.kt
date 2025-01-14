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
import com.example.firstapp.ui.quran.components.SurahCard

@Composable
fun SurahTab() {
    val surahList = listOf(
        "Surah Al-Fatihah",
        "Surah Al-Baqarah",
        "Surah Al-Imran",
        "Surah An-Nisa",
        "Surah Al-Maidah"
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Surah List", modifier = Modifier.padding(16.dp))
        LazyColumn {
            itemsIndexed(surahList) { index, surahName ->
                SurahCard(surahName = surahName, surahNumber = index + 1)
            }
        }
    }
}