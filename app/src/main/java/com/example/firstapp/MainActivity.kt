package com.example.firstapp

import android.Manifest
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.firstapp.data.network.RetrofitClient
import com.example.firstapp.data.models.GeocodeResponse
import com.example.firstapp.ui.quran.QuranScreen
import com.example.firstapp.ui.theme.FirstAppTheme
import com.example.firstapp.ui.compass.Compass
import com.example.firstapp.ui.components.BottomNavigationBar
import com.example.firstapp.ui.components.PolicyButton
import com.example.firstapp.ui.components.Toolbar
import com.example.firstapp.ui.goals.GoalsScreen
import com.example.firstapp.ui.settings.SettingsScreen
import com.example.firstapp.utils.LocationHelper
import com.example.firstapp.ui.compass.CompassScreen
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Create a CompositionLocal to hold the dark theme state
val LocalIsDarkTheme = staticCompositionLocalOf { mutableStateOf(false) }

class MainActivity : ComponentActivity() {
    private lateinit var locationHelper: LocationHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationHelper = LocationHelper(this)
        setContent {
            // Get the dark theme state from SharedPreferences
            val isDarkThemeEnabled = remember { mutableStateOf(false) }
            val sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE)
            isDarkThemeEnabled.value = sharedPreferences.getBoolean("isDarkThemeEnabled", false)

            // Provide the dark theme state to the composition
            CompositionLocalProvider(LocalIsDarkTheme provides isDarkThemeEnabled) {
                FirstAppTheme(darkTheme = isDarkThemeEnabled.value) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val navController = rememberNavController()
                        MainScreenWithBottomNavigation(navController, locationHelper, ::getCoordinatesForCity)
                    }
                }
            }
        }
    }

    private fun getCoordinatesForCity(city: String) {
        val apiKey = "9bee538dcea04fbaa7e624f43b7eea07" // Replace with your API key
        val call = RetrofitClient.openCageService.getCoordinates(city, apiKey)

        call.enqueue(object : Callback<GeocodeResponse> {
            override fun onResponse(
                call: Call<GeocodeResponse>,
                response: Response<GeocodeResponse>
            ) {
                if (response.isSuccessful) {
                    val geocodeResponse = response.body()
                    if (geocodeResponse != null && geocodeResponse.results.isNotEmpty()) {
                        val latitude = geocodeResponse.results[0].geometry.lat
                        val longitude = geocodeResponse.results[0].geometry.lng
                        val formattedAddress = geocodeResponse.results[0].formatted
                        println("Latitude: $latitude, Longitude: $longitude, Formatted Address: $formattedAddress")
                        // Update UI or use coordinates
                    } else {
                        println("No results found for city: $city")
                    }
                } else {
                    println("API call failed with code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<GeocodeResponse>, t: Throwable) {
                println("API call failed: ${t.message}")
            }
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenWithBottomNavigation(
    navController: NavHostController,
    locationHelper: LocationHelper,
    getCoordinatesForCity: (String) -> Unit
) {
    var currentCity by remember { mutableStateOf("Your City") }
    val coroutineScope = rememberCoroutineScope()
    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    var permissionsGranted by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissionsGranted = permissions.values.all { it }
    }

    LaunchedEffect(key1 = true) {
        launcher.launch(locationPermissions)
    }

    // Settings State
    val isDarkThemeEnabled = remember { mutableStateOf(false) }
    val isAutoLocationEnabled = remember { mutableStateOf(false) }
    val sharedPreferences = LocalContext.current.getSharedPreferences("settings", Context.MODE_PRIVATE)
    isAutoLocationEnabled.value = sharedPreferences.getBoolean("isAutoLocationEnabled", false)
    isDarkThemeEnabled.value = sharedPreferences.getBoolean("isDarkThemeEnabled", false)

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Get the dark theme state from the CompositionLocal
    val isDarkTheme = LocalIsDarkTheme.current

    // Update the isDarkThemeEnabled state when the CompositionLocal changes
    LaunchedEffect(isDarkTheme.value) {
        isDarkThemeEnabled.value = isDarkTheme.value
    }
    LaunchedEffect(isAutoLocationEnabled.value) {
        if (isAutoLocationEnabled.value) {
            val city = locationHelper.getCurrentCity()
            city?.let {
                currentCity = it
                getCoordinatesForCity(it) // Use the lambda here
            }
        }
    }

    Scaffold(
        topBar = {
            Toolbar(
                title = if (currentRoute == "settings") "Settings" else currentCity,
                onTitleClick = {
                    if (!isAutoLocationEnabled.value) {
                        navController.navigate("city_selection")
                    } else {
                        coroutineScope.launch {
                            val city = locationHelper.getCurrentCity()
                            city?.let {
                                currentCity = it
                                getCoordinatesForCity(it)
                            }
                        }
                    }
                },
                navigationIcon = {
                    if (currentRoute == "settings" || currentRoute == "city_selection") {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                        }
                    } else {
                        PolicyButton()
                    }
                },
                actions = {
                    if (currentRoute != "settings" && currentRoute != "city_selection") {
                        IconButton(onClick = { navController.navigate("settings") }) {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = "Settings"
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            if (currentRoute != "settings" && currentRoute != "city_selection") {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "main",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("main") { MainScreen(navController) }
            composable("activity") { ActivityScreen() }
            composable("goals") { GoalsScreen() }
            composable("profile") { ProfileScreen() }
            composable("compass_page") { CompassScreen() }
            composable("quran_page") { QuranScreen() }
            composable("settings") {
                SettingsScreen()
            }
            composable("city_selection") {
                // Removed OpenStreetMapScreen
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Top 3/4 of the screen (empty for now)
        Spacer(modifier = Modifier.weight(3f))

        // Bottom 1/4 of the screen (swipeable buttons)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Takes up 1/4 of the screen
        ) {
            val pagerState = rememberPagerState(pageCount = { 4 })

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                pageSpacing = 16.dp,
                pageSize = PageSize.Fixed(150.dp),
                flingBehavior = PagerDefaults.flingBehavior(
                    state = pagerState,
                    pagerSnapDistance = PagerSnapDistance.atMost(4)
                )
            ) { page ->
                when (page) {
                    0 -> MyButton(
                        text = "Kibla Direction",
                        navController = navController,
                        route = "compass_page"
                    )

                    1 -> MyButton(
                        text = "Quran Kareem",
                        navController = navController,
                        route = "quran_page"
                    )

                    2 -> MyButton(
                        text = "Bottom Left 2",
                        navController = navController,
                        route = "page_3"
                    )

                    3 -> MyButton(
                        text = "Bottom Right 2",
                        navController = navController,
                        route = "page_4"
                    )
                }
            }
        }
    }
}

@Composable
fun MyButton(
    text: String,
    navController: NavHostController,
    route: String,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = {
            println("$text clicked!")
            navController.navigate(route)
        },
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Gray, // Changed to gray
            contentColor = Color.White
        )
    ) {
        Text(text, textAlign = TextAlign.Center)
    }
}

@Composable
fun ActivityScreen() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Activity Screen")
        }
    }
}

@Composable
fun ProfileScreen() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Profile Screen")
        }
    }
}

@Composable
fun NewPage(pageNumber: Int) {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        // You can add content to your new page here
    }
}

@Composable
fun CompassScreen() {
    val context = LocalContext.current
    val compass = remember { Compass(context) }
    var azimuth by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(key1 = compass) {
        compass.start()
        compass.setListener(object : Compass.CompassListener {
            override fun onNewAzimuth(azimuthValue: Float) {
                azimuth = azimuthValue
            }
        })
    }

    LaunchedEffect(Unit) {
        compass.start()
    }

    DisposableEffect(Unit) {
        onDispose {
            compass.stop()
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.dial),
                    contentDescription = "Compass Dial",
                    modifier = Modifier.size(300.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.hands),
                    contentDescription = "Compass Hands",
                    modifier = Modifier
                        .size(250.dp)
                        .rotate(azimuth)
                )
            }
            Text(text = "Azimuth: $azimuth", modifier = Modifier.padding(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val navController = rememberNavController()
    MainScreen(navController)
}