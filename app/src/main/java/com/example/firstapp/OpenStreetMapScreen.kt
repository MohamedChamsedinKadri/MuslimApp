package com.example.firstapp

import android.content.Context
import android.location.Geocoder
import android.os.Build
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.data.position
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenStreetMapScreen(
    navController: NavHostController,
    onCitySelected: (String) -> Unit
) {
    val context = LocalContext.current
    var selectedCity by remember { mutableStateOf<String?>(null) }
    var map by remember { mutableStateOf<MapView?>(null) }
    val geocoder = remember { Geocoder(context, Locale.getDefault()) }

    // Initialize OSMDroid configuration
    Configuration.getInstance().load(
        context,
        context.getSharedPreferences("osm_prefs", Context.MODE_PRIVATE)
    )

    Column(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            factory = { context ->
                MapView(context).apply {
                    // Set the tile source (map data provider)
                    setTileSource(TileSourceFactory.MAPNIK)

                    // Set the initial map center and zoom level
                    controller.setZoom(10.0)
                    controller.setCenter(GeoPoint(34.0205, -6.8416)) // Example: Rabat, Morocco

                    // Add a compass overlay
                    val compassOverlay = CompassOverlay(
                        context,
                        InternalCompassOrientationProvider(context),
                        this
                    )
                    compassOverlay.enableCompass()
                    overlays.add(compassOverlay)

                    // Add a location overlay (blue dot)
                    val locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), this)
                    locationOverlay.enableMyLocation()
                    overlays.add(locationOverlay)

                    // Add a marker
                    val marker = Marker(this)
                    marker.position = GeoPoint(34.0205, -6.8416) // Example: Rabat, Morocco
                    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    marker.title = "Rabat, Morocco"
                    marker.snippet = "Capital of Morocco"
                    overlays.add(marker)

                    // Handle marker clicks
                    marker.setOnMarkerClickListener { marker, _ ->
                        selectedCity = marker.title
                        true
                    }
                    map = this
                }
            },
            update = { mapView ->
                mapView.controller.setZoom(10.0)
            }
        )
        LaunchedEffect(map) {
            if (map != null) {
                try {
                    val addressList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        geocoder.getFromLocationName("Rabat", 1)
                    } else {
                        geocoder.getFromLocationName("Rabat", 1)
                    }
                    if (addressList != null && addressList.isNotEmpty()) {
                        val address = addressList[0]
                        val geoPoint = GeoPoint(address.latitude, address.longitude)
                        map!!.controller.setCenter(geoPoint)
                        selectedCity = "Rabat"
                    } else {
                        Log.e("CitySelectionScreen", "Address list is null or empty")
                    }
                } catch (e: Exception) {
                    Log.e("CitySelectionScreen", "Error getting location: ${e.message}")
                }
            }
        }
        LaunchedEffect(selectedCity) {
            if (selectedCity != null) {
                onCitySelected(selectedCity!!)
                navController.popBackStack()
            }
        }
    }
}