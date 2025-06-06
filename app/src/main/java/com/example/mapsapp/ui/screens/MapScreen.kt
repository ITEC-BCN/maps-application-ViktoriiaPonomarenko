package com.example.mapsapp.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mapsapp.viewmodels.SupaBaseViewModel
import com.example.mapsapp.ui.navigation.Destination.MarkerCreation
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import com.example.mapsapp.ui.navigation.Destination.MarkerDatails


@Composable
fun MapScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    val viewModel = viewModel<SupaBaseViewModel>()
    val marcadorList by viewModel.marcadorList.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.getAllMarcadores()
    }

    val itb = LatLng(41.4534225, 2.1837151)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(itb, 17f)
    }

    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        onMapLongClick = { latLng ->
            navController.navigate(MarkerCreation(latLng.latitude, latLng.longitude))
        }
    ) {
        marcadorList.forEach { marcador ->
            Marker(
                state = MarkerState(position = LatLng(marcador.latitud, marcador.longitud)),
                title = marcador.titulo,
                snippet = "Más detalles",
                onInfoWindowClick = {
                    navController.navigate(MarkerDatails(marcador.id.toString()))
                }
            )
        }

        Marker(
            state = MarkerState(position = itb),
            title = "ITB",
            snippet = "Marcador fijo"
        )
    }
}

