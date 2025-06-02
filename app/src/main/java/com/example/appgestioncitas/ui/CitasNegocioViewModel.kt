package com.example.appgestioncitas.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appgestioncitas.data.repository.RepositoryCitas
import com.example.appgestioncitas.models.Cita

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
    fun editarEstadoCita(context:Context , cita: Cita) {
        repository.editarCita(context,cita)
    }

}