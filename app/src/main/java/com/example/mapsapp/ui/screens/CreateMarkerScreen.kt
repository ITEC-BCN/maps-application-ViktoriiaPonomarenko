package com.example.mapsapp.ui.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mapsapp.ui.navigation.Destination
import com.example.mapsapp.viewmodels.SupaBaseViewModel
import java.io.File


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateMarkerScreen(latitude: Double, longitude: Double, navController: NavHostController) {
    val myViewModel = viewModel<SupaBaseViewModel>()
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fotoUrl by remember { mutableStateOf("") }
    var insertado by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val takePictureLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success && imageUri.value != null) {
                val stream = context.contentResolver.openInputStream(imageUri.value!!)
                bitmap.value = BitmapFactory.decodeStream(stream)
            }
        }

    val pickImageLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imageUri.value = it
                val stream = context.contentResolver.openInputStream(it)
                bitmap.value = BitmapFactory.decodeStream(stream)
            }
        }

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

        Row(Modifier .fillMaxWidth()) {
            Button(onClick = {
                val uri = createImageUri(context)
                imageUri.value = uri
                takePictureLauncher.launch(uri!!)
                },
            ) {
                Text("Tomar Foto")
            }

            Button(
                onClick = {
                    pickImageLauncher.launch("image/*")
                },
            ) {
                Text("Elegir de Galería")
            }
        }


        Button(
            onClick = {
                myViewModel.insertNewMarcador(
                    titulo,
                    descripcion,
                    bitmap.value,
                    latitude,
                    longitude
                )
                insertado = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Insertar marcador")
        }

        bitmap.value?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .size(300.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
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


fun createImageUri(context: Context): Uri? {
    val file = File.createTempFile("temp_image_", ".jpg", context.cacheDir).apply {
        createNewFile()
        deleteOnExit()
    }

    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        file
    )
}