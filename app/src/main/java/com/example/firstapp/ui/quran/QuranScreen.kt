package com.example.firstapp.ui.quran

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firstapp.R
import com.example.firstapp.ui.quran.tabs.FavoritesTab
import com.example.firstapp.ui.quran.tabs.JuzTab
import com.example.firstapp.ui.quran.tabs.SurahTab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuranScreen(
    viewModel: QuranViewModel = viewModel()
) {
    val selectedTabIndex by viewModel.selectedTabIndex
    val tabs = listOf("Surah", "Juz", "Favorites")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.quran_title),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, tab ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { viewModel.onTabSelected(index) },
                        text = { Text(text = tab) }
                    )
                }
            }

            when (selectedTabIndex) {
                0 -> SurahTab()
                1 -> JuzTab()
                2 -> FavoritesTab()
            }
        }
    }
}