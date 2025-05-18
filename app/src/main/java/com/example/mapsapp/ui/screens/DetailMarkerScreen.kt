package com.example.mapsapp.ui.screens

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.mapsapp.viewmodels.SupaBaseViewModel

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailMarkerScreen(marcadorId: String, navigateBack: () -> Unit) {
    val context = LocalContext.current
    val myViewModel = viewModel<SupaBaseViewModel>()
    myViewModel.getMarcador(marcadorId)

    val marcadorTitulo by myViewModel.marcadorTitulo.observeAsState("")
    val marcadorDescripcion by myViewModel.marcadorDescripcion.observeAsState("")
    val marcadorFoto by myViewModel.marcadorFoto.observeAsState("")
    val marcadorFotoBitmap by myViewModel.marcadorFotoBitmap.observeAsState(null)

    var showImagePicker by remember { mutableStateOf(false) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { bitmap ->
            if (bitmap != null) {
                myViewModel.editMarcadorFotoBitmap(bitmap)
                Toast.makeText(context, "Foto tomada con éxito", Toast.LENGTH_SHORT).show()
                Toast.makeText(context, "Para confirmar los cambios, presiona el botón 'Update'", Toast.LENGTH_LONG).show()
            }
        }
    )

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                val bitmap: Bitmap = if (Build.VERSION.SDK_INT < 28) {
                    MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, uri)
                    ImageDecoder.decodeBitmap(source)
                }
                myViewModel.editMarcadorFotoBitmap(bitmap)
                Toast.makeText(context, "Imagen cargada desde la galería", Toast.LENGTH_SHORT).show()
                Toast.makeText(context, "Para confirmar los cambios, presiona el botón 'Update'", Toast.LENGTH_LONG).show()
            }
        }
    )

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = marcadorTitulo,
            onValueChange = { myViewModel.editMarcadorTitulo(it) },
            label = { Text("Título") }
        )
        TextField(
            value = marcadorDescripcion,
            onValueChange = { myViewModel.editMarcadorDescripcion(it) },
            label = { Text("Descripción") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        val fotoBitmap = marcadorFotoBitmap

        if (fotoBitmap != null) {
            // Локальный Bitmap - сразу показываем
            Image(
                bitmap = fotoBitmap.asImageBitmap(),
                contentDescription = "Foto seleccionada",
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .combinedClickable(onClick = {})
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { showImagePicker = true }) {
                    Text("Actualizar Imagen")
                }
                Button(onClick = {
                    myViewModel.editMarcadorFotoBitmap(null)
                    myViewModel.editMarcadorFoto("")
                    Toast.makeText(context, "Imagen eliminada", Toast.LENGTH_SHORT).show()
                    Toast.makeText(context, "Para confirmar los cambios, presiona el botón 'Update'", Toast.LENGTH_LONG).show()
                }) {
                    Text("Eliminar Imagen")
                }
            }

        } else if (!marcadorFoto.isNullOrEmpty()) {
            // Сетевой URL - показываем с лоадером
            var isLoading by remember { mutableStateOf(true) }
            val painter = rememberAsyncImagePainter(
                model = marcadorFoto,
                onState = { state ->
                    isLoading = when (state) {
                        is AsyncImagePainter.State.Loading -> true
                        is AsyncImagePainter.State.Success,
                        is AsyncImagePainter.State.Error,
                        is AsyncImagePainter.State.Empty -> false
                        else -> false
                    }
                }
            )

            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .combinedClickable(onClick = {}),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.foundation.Image(
                    painter = painter,
                    contentDescription = "Foto del marcador",
                    modifier = Modifier.matchParentSize()
                )

                if (isLoading) {
                    CircularProgressIndicator()
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { showImagePicker = true }) {
                    Text("Actualizar Imagen")
                }
                Button(onClick = {
                    myViewModel.editMarcadorFotoBitmap(null)
                    myViewModel.editMarcadorFoto("")
                    Toast.makeText(context, "Imagen eliminada", Toast.LENGTH_SHORT).show()
                    Toast.makeText(context, "Para confirmar los cambios, presiona el botón 'Update'", Toast.LENGTH_LONG).show()

                }) {
                    Text("Eliminar Imagen")
                }
            }

        } else {
            Text("No hay foto")

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = { showImagePicker = true }) {
                Text("Agregar Foto")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            myViewModel.updateMarcador(
                id = marcadorId,
                titulo = marcadorTitulo,
                descripcion = marcadorDescripcion,
                fotoBitmap = marcadorFotoBitmap
            )
            Toast.makeText(context, "Marcador actualizado", Toast.LENGTH_SHORT).show()
            navigateBack()
        }) {
            Text("Update")
        }
    }

    if (showImagePicker) {
        AlertDialog(
            onDismissRequest = { showImagePicker = false },
            title = { Text("Selecciona una opción") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = {
                        cameraLauncher.launch(null)
                        showImagePicker = false
                    }) {
                        Text("Tomar Foto")
                    }
                    Button(onClick = {
                        galleryLauncher.launch("image/*")
                        showImagePicker = false
                    }) {
                        Text("Subir desde Galería")
                    }
                }
            },
            confirmButton = {}
        )
    }
}
