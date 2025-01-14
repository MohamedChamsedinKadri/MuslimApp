package com.example.firstapp.data.network


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val QURAN_BASE_URL = "https://api.quran.com/api/v4/"
    private const val OPEN_CAGE_BASE_URL = "https://api.opencagedata.com/"

    private val quranOkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private val openCageOkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    val quranApiService: QuranApiService by lazy {
        Retrofit.Builder()
            .baseUrl(QURAN_BASE_URL)
            .client(quranOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuranApiService::class.java)
    }

    val openCageService: OpenCageService by lazy {
        Retrofit.Builder()
            .baseUrl(OPEN_CAGE_BASE_URL)
            .client(openCageOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenCageService::class.java)
    }
}