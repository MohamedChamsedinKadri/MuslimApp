package com.example.firstapp.ui.quran

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firstapp.data.Juz
import com.example.firstapp.data.Surah
import com.example.firstapp.data.Verse

@Composable
fun QuranScreen(viewModel: QuranViewModel = viewModel()) {
    val surahs by viewModel.surahs.collectAsState()
    val juzs by viewModel.juzs.collectAsState()
    val verses by viewModel.verses.collectAsState()

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var selectedSurahId by remember { mutableIntStateOf(0) }
    var showVerses by remember { mutableStateOf(false) }

    val tabs = listOf("Surah", "Juz", "Favorites")

    Column {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        showVerses = false // Hide verses when switching tabs
                    }
                )
            }
        }

        // Content based on selected tab
        when (selectedTabIndex) {
            0 -> {
                SurahScreen(surahs = surahs) { surahId ->
                    selectedSurahId = surahId
                    viewModel.loadVersesByChapter(surahId)
                    showVerses = true
                }
            }

            1 -> JuzScreen(juzs = juzs)
            2 -> FavoritesScreen()
        }
        if (showVerses) {
            VersesScreen(verses = verses)
        }
    }
}

@Composable
fun SurahScreen(surahs: List<Surah>, onSurahClick: (Int) -> Unit) {
    LazyColumn {
        items(surahs) { surah ->
            SurahCard(surah = surah, onSurahClick = onSurahClick)
        }
    }
}

@Composable
fun JuzScreen(juzs: List<Juz>) {
    LazyColumn {
        items(juzs) { juz ->
            JuzCard(juz = juz)
        }
    }
}

@Composable
fun FavoritesScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Favorites Screen", style = MaterialTheme.typography.headlineMedium)
        // TODO: Implement Favorites Screen
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurahCard(surah: Surah, onSurahClick: (Int) -> Unit) {
    Card(
        onClick = { onSurahClick(surah.id) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "${surah.id}. ${surah.name_simple}", style = MaterialTheme.typography.headlineSmall)
            Text(text = surah.translated_name.name, style = MaterialTheme.typography.bodyMedium)
            Text(text = "Ayahs: ${surah.verses_count}", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JuzCard(juz: Juz) {
    Card(
        onClick = { /* TODO: Handle Juz click */ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Juz ${juz.juz_number}", style = MaterialTheme.typography.headlineSmall)
            // You can display more Juz info here if needed
        }
    }
}

@Composable
fun VersesScreen(verses: List<Verse>) {
    LazyColumn {
        items(verses) { verse ->
            VerseCard(verse = verse)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerseCard(verse: Verse) {
    Card(
        onClick = { /* TODO: Handle Verse click */ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "${verse.verse_number}. ${verse.text_uthmani}", style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Right)
        }
    }
}