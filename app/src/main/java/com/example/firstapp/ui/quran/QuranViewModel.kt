package com.example.firstapp.ui.quran

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class QuranViewModel : ViewModel() {
    private val _selectedTabIndex = mutableStateOf(0)
    val selectedTabIndex: State<Int> = _selectedTabIndex

    fun onTabSelected(index: Int) {
        _selectedTabIndex.value = index
    }
}