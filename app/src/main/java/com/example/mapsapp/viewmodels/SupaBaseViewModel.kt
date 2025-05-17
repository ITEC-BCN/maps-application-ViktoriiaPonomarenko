package com.example.mapsapp.viewmodels

import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mapsapp.SupabaseApplication
import com.example.mapsapp.data.Marcador
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class SupaBaseViewModel(): ViewModel() {
    val database = SupabaseApplication.supabase

    private val _marcadorList = MutableLiveData<List<Marcador>>()
    val marcadorList = _marcadorList

    private val _selectedMarcador = MutableLiveData<String>()
    val selectedMarcador = _selectedMarcador


    private val _marcadorTitulo = MutableLiveData<String>()
    val marcadorTitulo = _marcadorTitulo

    private val _marcadorDescripcion = MutableLiveData<String>()
    val marcadorDescripcion = _marcadorDescripcion

    private val _marcadorFoto = MutableLiveData<String?>()
    val marcadorFoto = _marcadorFoto

    private val _marcadorLatitud = MutableLiveData<String>()
    val marcadorLatitud = _marcadorLatitud

    private val _marcadorLongitud = MutableLiveData<String>()
    val marcadorLongitud = _marcadorLongitud

    private val _marcadorFotoBitmap = MutableLiveData<Bitmap?>()
    val marcadorFotoBitmap = _marcadorFotoBitmap

    fun editMarcadorFotoBitmap(bitmap: Bitmap) {
        _marcadorFotoBitmap.postValue(bitmap)
    }

    fun getAllMarcadores() {
        CoroutineScope(Dispatchers.IO).launch {
            val databaseMarcador = database.getAllMarcadores()
            withContext(Dispatchers.Main) {
                _marcadorList.value = databaseMarcador
            }
        }
    }

//    @RequiresApi(Build.VERSION_CODES.O)
//    fun insertNewMarcador(titulo: String, descripcion: String, foto: Bitmap?, latitud: Double, longitud: Double) {
//        val stream = ByteArrayOutputStream()
//        foto?.compress(Bitmap.CompressFormat.PNG, 0, stream)
//        CoroutineScope(Dispatchers.IO).launch {
//
//            val imageName = database.uploadImage(stream.toByteArray())
//            val newMarcador = Marcador(titulo = titulo, descripcion = descripcion, foto = imageName, latitud = latitud, longitud = longitud)
//            database.insertMarcador(newMarcador)
//        }
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertNewMarcador(titulo: String, descripcion: String, foto: Bitmap?, latitud: Double, longitud: Double) {
        CoroutineScope(Dispatchers.IO).launch {
            // Si hay foto, súbela. Si no, usa string vacío.
            val fotoUrl = if (foto != null) {
                val stream = ByteArrayOutputStream()
                foto.compress(Bitmap.CompressFormat.PNG, 100, stream)
                database.uploadImage(stream.toByteArray())
            } else {
                ""
            }

            val nuevoMarcador = Marcador(
                titulo = titulo,
                descripcion = descripcion,
                foto = fotoUrl,
                latitud = latitud,
                longitud = longitud
            )

            database.insertMarcador(nuevoMarcador)
            getAllMarcadores() // opcional si quieres actualizar la lista
        }
    }




    @RequiresApi(Build.VERSION_CODES.O)
    fun updateMarcador(id: String, titulo: String, descripcion: String, fotoBitmap: Bitmap?) {
        CoroutineScope(Dispatchers.IO).launch {
            var fotoUrlActualizada = _marcadorFoto.value ?: ""

            if (fotoBitmap != null) {
                val stream = ByteArrayOutputStream()
                fotoBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val byteArray = stream.toByteArray()
                val newImageUrl = database.uploadImage(byteArray)
                fotoUrlActualizada = newImageUrl
            }

            database.updateMarcador(id, titulo, descripcion, fotoUrlActualizada)
            getAllMarcadores()
        }
    }




    fun deleteMarcador(id: String){
        CoroutineScope(Dispatchers.IO).launch {
            database.deleteMarcador(id)
            getAllMarcadores()
        }
    }

    fun getMarcador(id: String) {
        if (_selectedMarcador.value.isNullOrEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                val marcador = database.getMarcador(id)
                withContext(Dispatchers.Main) {
                    _selectedMarcador.value = id
                    _marcadorTitulo.value = marcador.titulo
                    _marcadorDescripcion.value = marcador.descripcion
                    _marcadorFoto.value = marcador.foto
                    _marcadorLatitud.value = marcador.latitud.toString()
                    _marcadorLongitud.value = marcador.longitud.toString()
                }
            }
        }
    }


    fun editMarcadorTitulo(titulo: String) {
        _marcadorTitulo.value = titulo
    }

    fun editMarcadorDescripcion(descripcion: String) {
        _marcadorDescripcion.value = descripcion
    }

    fun editMarcadorFoto(foto: String) {
        _marcadorFoto.value = foto
    }


}