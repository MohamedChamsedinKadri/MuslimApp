package com.example.firstapp.data.models

data class Verse(
    val id: Int,
    val surahId: Int,
    val verseNumber: Int,
    val text: String,
    val translation: String? = null
)