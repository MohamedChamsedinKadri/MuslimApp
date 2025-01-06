package com.example.firstapp.data

import com.example.firstapp.network.QuranApi

class QuranRepository {
    suspend fun getSurahs(): List<Surah> {
        return QuranApi.retrofit.getSurahs().chapters
    }

    suspend fun getJuzs(): List<Juz> {
        return QuranApi.retrofit.getJuzs().juzs
    }

    suspend fun getVersesByChapter(chapterId: Int): List<Verse> {
        return QuranApi.retrofit.getVersesByChapter(chapterId).verses
    }
}