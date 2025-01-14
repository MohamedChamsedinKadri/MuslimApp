package com.example.firstapp.data.models

data class Juz(
    val id: Int,
    val juzNumber: Int,
    val firstVerseId: Int,
    val lastVerseId: Int,
    val verses: List<Verse>
)