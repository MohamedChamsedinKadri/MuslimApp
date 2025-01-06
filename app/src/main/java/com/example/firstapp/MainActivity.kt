package com.example.firstapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firstapp.ui.theme.FirstAppTheme
import com.example.firstapp.ui.quran.QuranScreen // Import the new QuranScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "main") {
                        composable("main") { MainScreen(navController) }
                        composable("page_1") { NewPage(pageNumber = 1) }
                        composable("page_2") { NewPage(pageNumber = 2) }
                        composable("page_3") { NewPage(pageNumber = 3) }
                        composable("page_4") { NewPage(pageNumber = 4) }
                        composable("compass_page") { CompassScreen() }
                        composable("quran_page") { QuranScreen() } // Use the new QuranScreen here
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.weight(1f)) // Pushes the buttons to the bottom
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            MyButton(
                text = "Kibla Direction",
                modifier = Modifier.weight(1f),
                navController = navController,
                route = "compass_page"
            )
            MyButton(
                text = "Quran Kareem", // Changed text
                modifier = Modifier.weight(1f),
                navController = navController,
                route = "quran_page" // Changed route
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            MyButton(
                text = "Bottom Left 2",
                modifier = Modifier.weight(1f),
                navController = navController,
                route = "page_3"
            )
            MyButton(
                text = "Bottom Right 2",
                modifier = Modifier.weight(1f),
                navController = navController,
                route = "page_4"
            )
        }
    }
}

@Composable
fun MyButton(
    text: String,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    route: String
) {
    Button(
        onClick = {
            println("$text clicked!")
            navController.navigate(route)
        },
        modifier = modifier
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Gray, // Changed to gray
            contentColor = Color.White
        )
    ) {
        Text(text)
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