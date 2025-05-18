package com.example.mapsapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mapsapp.data.Marcador
import com.example.mapsapp.viewmodels.SupaBaseViewModel

@Composable
fun MarkerListScreen(navegarADetalle: (String) -> Unit) {
    val miViewModel = viewModel<SupaBaseViewModel>()
    val listaMarcadores by miViewModel.marcadorList.observeAsState(emptyList<Marcador>())
    miViewModel.getAllMarcadores()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            "Lista de Marcadores",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(items = listaMarcadores, key = { it.id!! }) { marcador ->
                val contexto = LocalContext.current
                val estadoDismiss = rememberSwipeToDismissBoxState(
                    confirmValueChange = {
                        if (it == SwipeToDismissBoxValue.EndToStart) {
                            miViewModel.deleteMarcador(marcador.id.toString())
                            Toast.makeText(contexto, "Marcador eliminado", Toast.LENGTH_SHORT).show()
                            true
                        } else {
                            false
                        }
                    }
                )
                val forma = RoundedCornerShape(12.dp)
                SwipeToDismissBox(
                    state = estadoDismiss,
                    backgroundContent = {
                        Box(
                            Modifier
                                .fillMaxSize()
                                .clip(forma)
                                .background(Color.Red)
                                .padding(horizontal = 20.dp),
                            contentAlignment = Alignment.BottomEnd
                        ) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar")
                        }
                    }
                ) {
                    ItemMarcador(marcador) { navegarADetalle(marcador.id.toString()) }
                }
            }
        }
    }
}

@Composable
fun ItemMarcador(marcador: Marcador, navegarADetalle: (String) -> Unit) {
    val forma = RoundedCornerShape(12.dp)
    Box(
        modifier = Modifier
            .fillMaxWidth(0.66f)
            .background(Color.White, forma)
            .border(width = 2.dp, color = Color(0xFF34A853), shape = forma)
            .clickable { navegarADetalle(marcador.id.toString()) }
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = marcador.titulo,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}
