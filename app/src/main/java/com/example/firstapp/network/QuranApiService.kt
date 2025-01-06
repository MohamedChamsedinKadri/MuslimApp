package com.example.firstapp.network

import com.example.firstapp.data.Juz
import com.example.firstapp.data.Surah
import com.example.firstapp.data.Verse
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path

interface QuranApiService {
    @GET("chapters")
    suspend fun getSurahs(): SurahsResponse

    @GET("juzs")
    suspend fun getJuzs(): JuzsResponse

    @GET("verses/by_chapter/{chapter_id}")
    suspend fun getVersesByChapter(@Path("chapter_id") chapterId: Int): VersesResponse
}

data class SurahsResponse(val chapters: List<Surah>)
data class JuzsResponse(val juzs: List<Juz>)
data class VersesResponse(val verses: List<Verse>)

object QuranApi {
    private const val BASE_URL = "https://api.quran.com/api/v4/"

    val retrofit: QuranApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuranApiService::class.java)
    }
}