package com.example.firstapp.data.models

data class Surah(
    val id: Int,
    val name: String,
    val englishName: String,
    val englishNameTranslation: String,
    val revelationType: String,
    val numberOfAyahs: Int
)