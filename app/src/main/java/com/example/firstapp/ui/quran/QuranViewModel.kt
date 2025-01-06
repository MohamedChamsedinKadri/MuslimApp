package com.example.firstapp.ui.quran

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstapp.data.Juz
import com.example.firstapp.data.QuranRepository
import com.example.firstapp.data.Surah
import com.example.firstapp.data.Verse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuranViewModel(private val repository: QuranRepository = QuranRepository()) : ViewModel() {

    private val _surahs = MutableStateFlow<List<Surah>>(emptyList())
    val surahs: StateFlow<List<Surah>> = _surahs.asStateFlow()

    private val _juzs = MutableStateFlow<List<Juz>>(emptyList())
    val juzs: StateFlow<List<Juz>> = _juzs.asStateFlow()

    private val _verses = MutableStateFlow<List<Verse>>(emptyList())
    val verses: StateFlow<List<Verse>> = _verses.asStateFlow()

    init {
        loadSurahs()
        loadJuzs()
    }

    private fun loadSurahs() {
        viewModelScope.launch {
            _surahs.value = repository.getSurahs()
        }
    }

    private fun loadJuzs() {
        viewModelScope.launch {
            _juzs.value = repository.getJuzs()
        }
    }

    fun loadVersesByChapter(chapterId: Int) {
        viewModelScope.launch {
            _verses.value = repository.getVersesByChapter(chapterId)
        }
    }
}