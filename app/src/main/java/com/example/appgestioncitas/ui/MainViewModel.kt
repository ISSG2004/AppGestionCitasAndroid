package com.example.appgestioncitas.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appgestioncitas.data.repository.RepositoryNegocio
import com.example.appgestioncitas.models.Negocio

class MainViewModel:ViewModel() {
    private val repository = RepositoryNegocio()
    private val _negocios = MutableLiveData<List<Negocio>>()
    val negocios: LiveData<List<Negocio>> get() = _negocios

    fun cargarNegocios() {
        repository.cargarNegocios { lista ->
            //Log.d("MainViewModel", "Negocios recibidos: ${lista.size}")
            _negocios.value = lista
        }
    }
}