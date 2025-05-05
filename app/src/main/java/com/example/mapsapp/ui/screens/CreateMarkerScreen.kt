package com.example.mapsapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mapsapp.ui.navigation.Destination
import com.example.mapsapp.viewmodels.SupaBaseViewModel


@Composable
fun CreateMarkerScreen(latitude: Double, longitude: Double, navController: NavHostController) {
    val myViewModel = viewModel<SupaBaseViewModel>()

    // Estados locales para los campos de texto
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fotoUrl by remember { mutableStateOf("") }

    var insertado by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(top = 150.dp, bottom = 100.dp, start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text("Crear nuevo marcador", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = fotoUrl,
            onValueChange = { fotoUrl = it },
            label = { Text("URL de la foto") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                myViewModel.insertNewMarcador(
                    titulo,
                    descripcion,
                    fotoUrl,
                    latitude,
                    longitude
                )
                insertado = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Insertar marcador")
        }

        // Navegar tras insertar
        if (insertado) {
            LaunchedEffect(Unit) {
                navController.navigate(Destination.Map) {
                    popUpTo<Destination.Map>() { inclusive = false }
                }
            }
        }
    }
}