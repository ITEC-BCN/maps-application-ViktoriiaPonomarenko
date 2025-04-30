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

    private val _marcadorTitle = MutableLiveData<String>()
    val marcadorTitle = _marcadorTitle

    private val _marcadorDescripcion = MutableLiveData<String>()
    val marcadorDescripcion = _marcadorDescripcion

    fun getAllMarcadores() {
        CoroutineScope(Dispatchers.IO).launch {
            val databaseMarcador = database.getAllMarcadores()
            withContext(Dispatchers.Main) {
                _marcadorList.value = databaseStudents
            }
        }
    }

    fun insertNewStudent(name: String, mark: String) {
        val newStudent = Marcador(name = name, mark = mark.toDouble())
        CoroutineScope(Dispatchers.IO).launch {
            database.insertStudent(newStudent)
            getAllMarcadores()
        }
    }

    fun updateStudent(id: String, name: String, mark: String){
        CoroutineScope(Dispatchers.IO).launch {
            database.updateStudent(id, name, mark.toDouble())
        }
    }

    fun deleteStudent(id: String){
        CoroutineScope(Dispatchers.IO).launch {
            database.deleteStudent(id)
            getAllStudents()
        }
    }

    fun getStudent(id: String){
        if(_selectedStudent == null){
            CoroutineScope(Dispatchers.IO).launch {
                val student = database.getStudent(id)
                withContext(Dispatchers.Main) {
                    _selectedStudent = student
                    _studentName.value = student.name
                    _studentMark.value = student.mark.toString()
                }
            }
        }
    }

    fun editStudentName(name: String) {
        _studentName.value = name
    }

    fun editStudentMark(mark: String) {
        _studentMark.value = mark
    }
}