package com.example.firstapp.di

import android.content.Context
import com.example.firstapp.data.QuranRepository
import com.example.firstapp.data.network.QuranApiService
import com.example.firstapp.data.network.RetrofitClient
import com.example.firstapp.utils.LocationHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideQuranApiService(): QuranApiService {
        return RetrofitClient.quranApiService
    }

    @Provides
    @Singleton
    fun provideQuranRepository(quranApiService: QuranApiService): QuranRepository {
        return QuranRepository(quranApiService)
    }

    @Provides
    @Singleton
    fun provideLocationHelper(@ApplicationContext context: Context): LocationHelper {
        return LocationHelper(context)
    }
}