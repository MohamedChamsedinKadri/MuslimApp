package com.example.firstapp

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.edit

@Composable
fun SettingsScreen(
    isDarkThemeEnabled: Boolean,
    onDarkThemeChange: (Boolean) -> Unit,
    isAutoLocationEnabled: Boolean,
    onAutoLocationChange: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val sharedPreferences = remember {
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    }

    // Load settings from SharedPreferences on initial composition
    var currentDarkThemeEnabled by remember {
        mutableStateOf(sharedPreferences.getBoolean("isDarkThemeEnabled", false))
    }
    var currentAutoLocationEnabled by remember {
        mutableStateOf(sharedPreferences.getBoolean("isAutoLocationEnabled", false))
    }

    // Update the UI state when the external state changes
    LaunchedEffect(isDarkThemeEnabled) {
        currentDarkThemeEnabled = isDarkThemeEnabled
    }
    LaunchedEffect(isAutoLocationEnabled) {
        currentAutoLocationEnabled = isAutoLocationEnabled
    }

    // Auto-save settings to SharedPreferences and apply dark mode
    LaunchedEffect(currentDarkThemeEnabled, currentAutoLocationEnabled) {
        sharedPreferences.edit {
            putBoolean("isDarkThemeEnabled", currentDarkThemeEnabled)
            putBoolean("isAutoLocationEnabled", currentAutoLocationEnabled)
        }

        // Apply dark mode
        if (currentDarkThemeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        SettingsSwitchRow(
            text = "Dark Theme",
            checked = currentDarkThemeEnabled,
            onCheckedChange = {
                onDarkThemeChange(it)
                currentDarkThemeEnabled = it
            }
        )

        SettingsSwitchRow(
            text = "Auto Location",
            checked = currentAutoLocationEnabled,
            onCheckedChange = {
                onAutoLocationChange(it)
                currentAutoLocationEnabled = it
            }
        )
    }
}

@Composable
fun SettingsSwitchRow(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}