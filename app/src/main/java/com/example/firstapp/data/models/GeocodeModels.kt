package com.example.firstapp.data.models

data class GeocodeResponse(
    val results: List<GeocodeResult>
)

data class GeocodeResult(
    val formatted: String,
    val geometry: Geometry
)

data class Geometry(
    val lat: Double,
    val lng: Double
)