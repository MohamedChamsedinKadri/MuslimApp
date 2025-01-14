package com.example.firstapp.ui.compass

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import com.example.firstapp.R
import com.example.firstapp.utils.Compass

@Composable
fun CompassScreen() {
    val context = LocalContext.current
    val compass = remember { Compass(context) }
    var azimuth by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(key1 = compass) {
        compass.setListener(object : Compass.CompassListener {
            override fun onNewAzimuth(azimuthValue: Float) {
                azimuth = azimuthValue
            }
        })
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