package com.example.mapsapp.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.mapsapp.ui.navigation.Destination.MarkerCreation
import com.example.mapsapp.ui.navigation.Destination.MarkerDatails
import com.example.mapsapp.ui.screens.CreateMarkerScreen
import com.example.mapsapp.ui.screens.DetailMarkerScreen
import com.example.mapsapp.ui.screens.LoginScreen
import com.example.mapsapp.ui.screens.MapScreen
import com.example.mapsapp.ui.screens.MarkerListScreen
import com.example.mapsapp.ui.screens.RegistrScreen


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InternalNavigationWrapper(navController: NavHostController, padding: Modifier) {
    NavHost(navController, Destination.Map){

        composable<Destination.Map> {
            MapScreen(navController = navController)
        }

        composable<Destination.List> {backStackEntry ->
            MarkerListScreen { id -> navController.navigate(MarkerDatails(id)) }
        }

        composable<MarkerDatails> { backStackEntry ->
            val detalle = backStackEntry.toRoute<MarkerDatails>()
            DetailMarkerScreen(detalle.id) {
                navController.navigate(Destination.List) {
                    popUpTo<Destination.List> { inclusive = true }
                }
            }
        }

        composable<MarkerCreation> { backStackEntry ->
            val args = backStackEntry.toRoute<MarkerCreation>()
            CreateMarkerScreen(latitude = args.latitude, longitude = args.longitude, navController = navController)
        }


    }

}
