package com.example.firstapp.data.network

import com.example.firstapp.data.models.Surah
import com.example.firstapp.data.models.Verse
import com.example.firstapp.data.models.Juz
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface QuranApiService {
    @GET("surah")
    suspend fun getAllSurahs(): Response<List<Surah>>

    @GET("surah/{surahId}")
    suspend fun getSurahById(@Path("surahId") surahId: Int): Response<Surah>

    @GET("juz")
    suspend fun getAllJuzs(): Response<List<Juz>>

    @GET("juz/{juzId}")
    suspend fun getJuzById(@Path("juzId") juzId: Int): Response<Juz>

    @GET("surah/{surahId}/ayahs")
    suspend fun getVersesBySurahId(@Path("surahId") surahId: Int): Response<List<Verse>>

    @GET("juz/{juzId}/ayahs")
    suspend fun getVersesByJuzId(@Path("juzId") juzId: Int): Response<List<Verse>>
}