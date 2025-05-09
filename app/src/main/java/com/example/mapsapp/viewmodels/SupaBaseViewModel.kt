package com.example.mapsapp.viewmodels

import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mapsapp.MyApp
import com.example.mapsapp.data.Marcador
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class SupaBaseViewModel: ViewModel() {

    val database = MyApp.database

    private val _marcadorList = MutableLiveData<List<Marcador>>()
    val marcadorList = _marcadorList

    private val _selectedMarcador = MutableLiveData<String>()
    val selectedMarcador = _selectedMarcador
    
    
    private val _marcadorTitulo = MutableLiveData<String>()
    val marcadorTitulo = _marcadorTitulo

    private val _marcadorDescripcion = MutableLiveData<String>()
    val marcadorDescripcion = _marcadorDescripcion

    private val _marcadorFoto = MutableLiveData<String>()
    val marcadorFoto = _marcadorFoto

    private val _marcadorLatitud = MutableLiveData<String>()
    val marcadorLatitud = _marcadorLatitud

    private val _marcadorLongitud = MutableLiveData<String>()
    val marcadorLongitud = _marcadorLongitud

    fun getAllMarcadores() {
        CoroutineScope(Dispatchers.IO).launch {
            val databaseMarcador = database.getAllMarcadores()
            withContext(Dispatchers.Main) {
                _marcadorList.value = databaseMarcador
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun insertNewMarcador(titulo: String, descripcion: String, foto: Bitmap?, latitud: Double, longitud: Double) {
        val stream = ByteArrayOutputStream()
        foto?.compress(Bitmap.CompressFormat.PNG, 0, stream)
        CoroutineScope(Dispatchers.IO).launch {

            val imageName = database.uploadImage(stream.toByteArray())
            val newMarcador = Marcador(titulo = titulo, descripcion = descripcion, foto = imageName, latitud = latitud, longitud = longitud)
            database.insertMarcador(newMarcador)
        }
    }

//    fun updateMarcador(id: String, titulo: String, descripcion: String, foto: String){
//        CoroutineScope(Dispatchers.IO).launch {
//            database.updateMarcador(id, titulo, descripcion, foto)
//        }
//    }

//    fun updateMarcador(id: String, titulo: String, descripcion: String, foto: Bitmap?){
//        val stream = ByteArrayOutputStream()
//        foto?.compress(Bitmap.CompressFormat.PNG, 0, stream)
//        val imageName = _selectedMarcador.value?.foto?.removePrefix("https://aobflzinjcljzqpxpcxs.supabase.co/storage/v1/object/public/images/")
//        CoroutineScope(Dispatchers.IO).launch {
//            database.updateMarcador(id, titulo, descripcion, imageName.toString(), stream.toByteArray())
//        }
//    }


    fun deleteMarcador(id: String){
        CoroutineScope(Dispatchers.IO).launch {
            database.deleteMarcador(id)
            getAllMarcadores()
        }
    }

    fun getMarcador(id: String){
        if(_selectedMarcador == null){
            CoroutineScope(Dispatchers.IO).launch {
                val marcador = database.getMarcador(id)
                withContext(Dispatchers.Main) {
                    _selectedMarcador = marcador
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