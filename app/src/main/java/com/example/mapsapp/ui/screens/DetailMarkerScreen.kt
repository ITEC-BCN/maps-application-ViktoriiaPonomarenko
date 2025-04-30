package com.example.mapsapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mapsapp.viewmodels.SupaBaseViewModel



@Composable
fun DetailMarkerScreen(marcadorId: String, navigateBack: () -> Unit){
    val myViewModel = viewModel<SupaBaseViewModel>()
    myViewModel.getMarcador(marcadorId)
    val marcadorTitulo: String by myViewModel.marcadorTitulo.observeAsState("")
    val marcadorDescripcion: String by myViewModel.marcadorDescripcion.observeAsState("")
    val marcadorFoto: String by myViewModel.marcadorFoto.observeAsState("")
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(value = marcadorTitulo, onValueChange = { myViewModel.editMarcadorTitulo(it) })
        TextField(value = marcadorDescripcion, onValueChange = { myViewModel.editMarcadorDescripcion(it) })
        Button(onClick = {
            myViewModel.updateMarcador(marcadorId, marcadorTitulo, marcadorDescripcion, marcadorFoto)
            navigateBack()
        }) {
            Text("Update")
        }
    }
}