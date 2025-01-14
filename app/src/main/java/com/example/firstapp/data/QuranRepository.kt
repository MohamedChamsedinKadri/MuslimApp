package com.example.firstapp.data

import com.example.firstapp.data.models.Surah
import com.example.firstapp.data.models.Verse
import com.example.firstapp.data.models.Juz
import com.example.firstapp.data.models.Favorite
import com.example.firstapp.data.network.QuranApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class QuranRepository(private val quranApiService: QuranApiService) {

    // Example function to fetch all Surahs
    fun getAllSurahs(): Flow<List<Surah>> = flow {
        val response = quranApiService.getAllSurahs()
        if (response.isSuccessful) {
            emit(response.body() ?: emptyList())
        } else {
            // Handle error (e.g., emit an empty list or throw an exception)
            emit(emptyList())
        }
    }

    // Example function to fetch a specific Surah by ID
    fun getSurahById(surahId: Int): Flow<Surah?> = flow {
        val response = quranApiService.getSurahById(surahId)
        if (response.isSuccessful) {
            emit(response.body())
        } else {
            // Handle error
            emit(null)
        }
    }

    // Example function to fetch all Juzs
    fun getAllJuzs(): Flow<List<Juz>> = flow {
        val response = quranApiService.getAllJuzs()
        if (response.isSuccessful) {
            emit(response.body() ?: emptyList())
        } else {
            // Handle error
            emit(emptyList())
        }
    }

    // Example function to fetch a specific Juz by ID
    fun getJuzById(juzId: Int): Flow<Juz?> = flow {
        val response = quranApiService.getJuzById(juzId)
        if (response.isSuccessful) {
            emit(response.body())
        } else {
            // Handle error
            emit(null)
        }
    }

    // Example function to fetch all Verses of a Surah
    fun getVersesBySurahId(surahId: Int): Flow<List<Verse>> = flow {
        val response = quranApiService.getVersesBySurahId(surahId)
        if (response.isSuccessful) {
            emit(response.body() ?: emptyList())
        } else {
            // Handle error
            emit(emptyList())
        }
    }
    // Example function to fetch all Verses of a Juz
    fun getVersesByJuzId(juzId: Int): Flow<List<Verse>> = flow {
        val response = quranApiService.getVersesByJuzId(juzId)
        if (response.isSuccessful) {
            emit(response.body() ?: emptyList())
        } else {
            // Handle error
            emit(emptyList())
        }
    }
    // Example function to add a verse to favorites
    fun addVerseToFavorites(verse: Verse): Flow<Boolean> = flow {
        // Simulate adding to favorites (replace with actual logic)
        emit(true) // Or false if there was an error
    }

    // Example function to remove a verse from favorites
    fun removeVerseFromFavorites(verse: Verse): Flow<Boolean> = flow {
        // Simulate removing from favorites (replace with actual logic)
        emit(true) // Or false if there was an error
    }

    // Example function to get all favorite verses
    fun getAllFavoriteVerses(): Flow<List<Favorite>> = flow {
        // Simulate getting all favorites (replace with actual logic)
        emit(emptyList()) // Replace with actual list of favorites
    }
}