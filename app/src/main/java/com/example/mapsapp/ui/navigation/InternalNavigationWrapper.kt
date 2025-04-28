package com.example.mapsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mapsapp.ui.navigation.Destination.Map
import com.example.mapsapp.ui.screens.MapScreen

@Composable
fun InternalNavigationWrapper(navController1: NavHostController, padding: Modifier) {
    val navController = rememberNavController()
    NavHost(navController, Map){

        composable<Map> {
            MapScreen()
        }

//        composable<List> {
//            MarkerListScreen()
//        }


    }
}