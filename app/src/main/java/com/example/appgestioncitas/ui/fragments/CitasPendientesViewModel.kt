package com.example.appgestioncitas.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appgestioncitas.data.repository.RepositoryCitas
import com.example.appgestioncitas.models.Cita

class CitasPendientesViewModel : ViewModel() {

    private val repository = RepositoryCitas()
    private val _citasPendientes = MutableLiveData<List<Cita>>()
    val citasPendientes: LiveData<List<Cita>> get() = _citasPendientes

    fun cargarCitasPendientes() {
        repository.cargarCitas { listaCompleta ->
            val pendientes = listaCompleta.filter { it.estado == "pendiente" }
            _citasPendientes.postValue(pendientes)
        }
    }
}
