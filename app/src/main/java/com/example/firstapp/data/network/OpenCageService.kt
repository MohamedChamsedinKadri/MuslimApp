package com.example.firstapp.data.network

import com.example.firstapp.data.models.GeocodeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenCageService {
    @GET("geocode/v1/json")
    fun getCoordinates(
        @Query("q") city: String,
        @Query("key") apiKey: String
    ): Call<GeocodeResponse>
}