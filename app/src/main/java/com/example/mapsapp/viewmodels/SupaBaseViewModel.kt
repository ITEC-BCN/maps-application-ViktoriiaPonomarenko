package com.example.mapsapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mapsapp.MyApp
import com.example.mapsapp.data.Marcador
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SupaBaseViewModel: ViewModel() {

    val database = MyApp.database

    private val _marcadorList = MutableLiveData<List<Marcador>>()
    val marcadorList = _marcadorList

    private var _selectedMarcador: Marcador? = null

    private val _marcadorTitulo = MutableLiveData<String>()
    val marcadorTitulo = _marcadorTitulo

    private val _marcadorDescripcion = MutableLiveData<String>()
    val marcadorDescripcion = _marcadorDescripcion

    private val _marcadorFoto = MutableLiveData<String>()
    val marcadorFoto = _marcadorFoto

    fun getAllMarcadores() {
        CoroutineScope(Dispatchers.IO).launch {
            val databaseMarcador = database.getAllMarcadores()
            withContext(Dispatchers.Main) {
                _marcadorList.value = databaseMarcador
            }
        }
    }

    fun insertNewMarcador(titulo: String, descripcion: String, foto: String, latitud: Double, longitud: Double) {
        val newMarcador = Marcador(titulo = titulo, descripcion = descripcion, foto = foto, latitud = latitud, longitud = longitud)
        CoroutineScope(Dispatchers.IO).launch {
            database.insertMarcador(newMarcador)
            getAllMarcadores()
        }
    }

    fun updateMarcador(id: String, titulo: String, descripcion: String, foto: String){
        CoroutineScope(Dispatchers.IO).launch {
            database.updateMarcador(id, titulo, descripcion, foto)
        }
    }

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