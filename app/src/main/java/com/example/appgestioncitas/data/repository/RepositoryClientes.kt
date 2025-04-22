package com.example.appgestioncitas.data.repository

import com.example.appgestioncitas.data.firebase.clientes.CrudClientes
import com.example.appgestioncitas.models.Usuario

class RepositoryClientes {
    fun crearCliente(cliente: Usuario,uidCliente:String) {
        CrudClientes().crearUsuario(cliente,uidCliente)
    }
}