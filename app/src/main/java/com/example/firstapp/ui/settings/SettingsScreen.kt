package com.example.firstapp.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.firstapp.R
import com.example.firstapp.ui.settings.components.SettingsSwitchRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    val isDarkModeEnabled = remember { mutableStateOf(false) }
    val isNotificationsEnabled = remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.settings_title),
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
                .padding(16.dp)
        ) {
            SettingsSwitchRow(
                text = stringResource(id = R.string.dark_mode),
                isChecked = isDarkModeEnabled.value,
                onCheckedChange = { isDarkModeEnabled.value = it }
            )
            SettingsSwitchRow(
                text = stringResource(id = R.string.notifications),
                isChecked = isNotificationsEnabled.value,
                onCheckedChange = { isNotificationsEnabled.value = it }
            )
        }
    }
}