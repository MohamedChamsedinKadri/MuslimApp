package com.example.firstapp

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.gms.common.api.Status

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitySelectionScreen(navController: NavHostController, onCitySelected: (String) -> Unit) {
    val context = LocalContext.current
    val componentActivity = context as ComponentActivity
    val fragmentManager = remember { componentActivity.supportFragmentManager }
    var selectedCity by remember { mutableStateOf<String?>(null) }
    val lifecycleOwner = LocalLifecycleOwner.current
    val fragment = remember { AutocompleteSupportFragment() }

    Column {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            factory = { _ ->
                fragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))
                fragment.setCountry("MA")
                fragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
                    override fun onPlaceSelected(place: Place) {
                        selectedCity = place.name
                    }

                    override fun onError(status: Status) {
                        Log.e("CitySelectionScreen", "Error: ${status.statusMessage}")
                    }
                })
                fragmentManager.beginTransaction().add(android.R.id.content, fragment).commit()
                fragment.requireView()
            },
            update = { _ ->
                // Update logic if needed
            }
        )
        LaunchedEffect(selectedCity) {
            if (selectedCity != null) {
                onCitySelected(selectedCity!!)
                navController.popBackStack()
            }
        }
        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_DESTROY) {
                    if (fragment.isAdded) {
                        fragmentManager.beginTransaction().remove(fragment).commit()
                    }
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
                if (fragment.isAdded) {
                    fragmentManager.beginTransaction().remove(fragment).commit()
                }
            }
        }
    }
}