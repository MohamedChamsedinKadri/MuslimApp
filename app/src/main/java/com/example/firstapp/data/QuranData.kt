package com.example.firstapp.data

data class Surah(
    val id: Int,
    val revelation_place: String,
    val revelation_order: Int,
    val bismillah_pre: Boolean,
    val name_simple: String,
    val name_complex: String,
    val name_arabic: String,
    val verses_count: Int,
    val pages: List<Int>,
    val translated_name: TranslatedName
)

data class TranslatedName(
    val language_name: String,
    val name: String
)

data class Juz(
    val id: Int,
    val juz_number: Int,
    val verses: List<VerseRange>
)

data class VerseRange(
    val id: Int,
    val chapter_id: Int,
    val verse_number: Int
)

data class Verse(
    val id: Int,
    val verse_number: Int,
    val verse_key: String,
    val text_uthmani: String
)

data class Favorite(
    val surahNumber: Int,
    val verseNumber: Int,
    val text: String
)