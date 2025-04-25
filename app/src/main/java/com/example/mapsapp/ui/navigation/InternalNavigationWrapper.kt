package com.example.mapsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mapsapp.ui.navigation.Destination.Map
import com.example.mapsapp.ui.screens.MapScreen

@Composable
fun InternalNavigationWrapper() {
    val navController = rememberNavController()
    NavHost(navController, Map){

        composable<Map> {
            MapScreen {
                navController.navigate(Map)
            }
        }
    }
}