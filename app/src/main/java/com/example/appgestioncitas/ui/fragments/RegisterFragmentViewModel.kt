package com.example.appgestioncitas.ui.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appgestioncitas.data.repository.RepositoryClientes
import com.example.appgestioncitas.models.Usuario

class RegisterFragmentViewModel: ViewModel() {
    val repository = RepositoryClientes()
    private val _listaCliente = MutableLiveData<Usuario>()
    val listaClienteRegistro : MutableLiveData<Usuario> = _listaCliente

    fun crearCliente(cliente: Usuario, uidCliente:String){
        repository.crearCliente(cliente,uidCliente)
    }
    fun crearClienteLogGoogle(cliente: Usuario, uidCliente:String){
        repository.crearClienteLogGoogle(cliente,uidCliente)
    }

    //metodos para la lista de clientes para validar si existe
}