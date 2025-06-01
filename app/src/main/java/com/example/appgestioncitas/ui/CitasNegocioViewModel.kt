package com.example.appgestioncitas.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appgestioncitas.data.repository.RepositoryCitas
import com.example.appgestioncitas.data.repository.RepositoryNegocio
import com.example.appgestioncitas.models.Cita
import com.example.appgestioncitas.models.Negocio

class CitasNegocioViewModel: ViewModel() {
    private val repository = RepositoryCitas()
    private val _citas = MutableLiveData<List<Cita>>()
    val citas: MutableLiveData<List<Cita>> get() = _citas

    fun cargarCitas() {
        repository.cargarCitas { lista ->
            _citas.value = lista
        }
    }
    fun cargarCitasDelNegocio(negocioId: String) {
        repository.cargarCitas { listaCompleta ->
            val citasFiltradas = listaCompleta.filter {
                it.negocio_id == negocioId && it.estado == "disponible"
            }
            _citas.value = citasFiltradas
        }
    }

}