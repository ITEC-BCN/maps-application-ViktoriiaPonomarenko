package com.example.mapsapp.ui.screens


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
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
import coil.compose.AsyncImage
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

        if (marcadorFoto.isNullOrEmpty()) {
            Text("No hay foto")
        } else {
            AsyncImage(
                model = marcadorFoto,
                contentDescription = "Foto del marcador",
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .combinedClickable(
                        onClick = {},
                        onLongClick = { showImagePicker = true }
                    )
            )
        }


        Button(onClick = {
            myViewModel.updateMarcador(
                id = marcadorId,
                titulo = marcadorTitulo,
                descripcion = marcadorDescripcion,
                fotoBitmap = marcadorFotoBitmap
            )
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
