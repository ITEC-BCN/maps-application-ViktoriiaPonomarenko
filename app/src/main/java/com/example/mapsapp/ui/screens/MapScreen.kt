package com.example.mapsapp.ui.screens


import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun MapScreen(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize()) {
        val itb = LatLng(41.4534225, 2.1837151)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(itb, 17f)
        }

        // Lista de marcadores
        val markerPosition = remember { mutableStateOf<LatLng?>(null) }

        GoogleMap(
            modifier = modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapClick = {
                Log.d("MAP CLICKED", it.toString())
            },
            onMapLongClick = { latLng ->
                Log.d("MAP CLICKED LONG", latLng.toString())
                markerPosition.value = latLng
            }

        ) {
            // Marcador fijo (ITB)
            Marker(
                state = MarkerState(position = itb),
                title = "ITB",
                snippet = "Marker at ITB"
            )

            // Marcador dinámico
            markerPosition.value?.let { position ->
                Marker(
                    state = MarkerState(position = position),
                    title = "Nuevo marcador",
                    snippet = "Clic en: $position"
                )
            }
        }
    }
}

