package com.example.appgestioncitas.ui

import androidx.lifecycle.ViewModel
import com.example.appgestioncitas.data.repository.RepositoryClientes
import com.example.appgestioncitas.models.Usuario

class PerfilViewModel: ViewModel()  {
    private val repositoryClientes = RepositoryClientes()

    fun cargarUsuario(uidFirebase: String, callback: (Usuario?) -> Unit) {
        repositoryClientes.cargarCliente(uidFirebase, callback)
    }
}