package com.example.appgestioncitas.ui.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appgestioncitas.data.repository.RepositoryClientes
import com.example.appgestioncitas.models.Usuario

class RegisterFragmentViewModel: ViewModel() {
    val repository = RepositoryClientes()
    private val _listaCliente = MutableLiveData<List<Usuario>>()
    val listaClienteRegistro : MutableLiveData<List<Usuario>> = _listaCliente
    private val _usuarioValido = MutableLiveData<Boolean>()
    val usuarioValido : MutableLiveData<Boolean> = _usuarioValido


    fun crearCliente(cliente: Usuario, uidCliente:String){
        repository.crearCliente(cliente,uidCliente)
    }
    fun crearClienteLogGoogle(cliente: Usuario, uidCliente:String){
        repository.crearClienteLogGoogle(cliente,uidCliente)
    }
    // Cargar todos los clientes
    fun cargarClientes(callback: (List<Usuario>) -> Unit) {
        repository.cargarClientes(callback)
    }

    // Método para validar si el correo del usuario ya existe
    fun validarUsuario(usuario: Usuario) {
        cargarClientes { clientes ->
            var usuarioDuplicado = false
            for (cliente in clientes) {
                if (cliente.correo == usuario.correo) {
                    usuarioDuplicado = true
                    break // Si encontramos un duplicado, salimos del ciclo
                }
            }
            // Actualizamos el estado de la validez del usuario
            _usuarioValido.value = !usuarioDuplicado
        }
    }
}