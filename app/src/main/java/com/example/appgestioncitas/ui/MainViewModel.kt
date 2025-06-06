package com.example.appgestioncitas.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appgestioncitas.data.repository.RepositoryCitas
import com.example.appgestioncitas.data.repository.RepositoryNegocio
import com.example.appgestioncitas.models.Cita
import com.example.appgestioncitas.models.Negocio

class MainViewModel:ViewModel() {
    private val repository = RepositoryCitas()
    private val repositoryNegocio = RepositoryNegocio()
    private val _citas = MutableLiveData<List<Cita>>()
    val citas: LiveData<List<Cita>> get() = _citas
    private val _negocios = MutableLiveData<List<Negocio>>()
    val negocios: LiveData<List<Negocio>> get() = _negocios

    fun cargarNegocios(){
        repositoryNegocio.cargarNegocios { lista ->
            _negocios.postValue(lista)
        }
    }

    fun editarEstadoCita(context: Context, cita: Cita) {
        repository.editarCita(context,cita)
    }
    fun cargarCitas() {
        repository.cargarCitas { citas ->
            _citas.postValue(citas)
        }
    }
}