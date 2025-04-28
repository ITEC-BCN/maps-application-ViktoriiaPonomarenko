package com.example.mapsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.mapsapp.ui.screens.MapScreen
import com.example.mapsapp.ui.theme.MapsAppTheme
import kotlin.time.TimeSource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Instala la pantalla de splash y la mantiene visible durante 3 segundos
        val splash = installSplashScreen()
        val now = TimeSource.Monotonic.markNow()
        splash.setKeepOnScreenCondition { now.elapsedNow().inWholeMilliseconds < 3000 }


        enableEdgeToEdge()
        setContent {
            MapsAppTheme {
                MapScreen()

            }
        }
    }
}

