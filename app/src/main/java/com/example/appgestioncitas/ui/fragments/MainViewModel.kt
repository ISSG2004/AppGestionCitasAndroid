package com.example.appgestioncitas.ui.fragments

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appgestioncitas.data.repository.RepositoryCitas
import com.example.appgestioncitas.models.Cita

class MainViewModel: ViewModel() {
    private val repository = RepositoryCitas()
    private val _citas = MutableLiveData<List<Cita>>()
    val citas: LiveData<List<Cita>> get() = _citas
    fun editarEstadoCita(context: Context, cita: Cita) {
        repository.editarCita(context,cita)
    }
    fun cargarCitas() {
        repository.cargarCitas { citas ->
            _citas.postValue(citas)
        }
    }
}