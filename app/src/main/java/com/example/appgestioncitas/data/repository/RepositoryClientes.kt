package com.example.appgestioncitas.data.repository

import com.example.appgestioncitas.data.firebase.clientes.CrudClientes
import com.example.appgestioncitas.models.Usuario

class RepositoryClientes {
    fun crearCliente(cliente: Usuario,uidCliente:String) {
        CrudClientes().crearUsuario(cliente,uidCliente)
    }
    fun crearClienteLogGoogle(cliente: Usuario,uidCliente:String) {
        CrudClientes().crearUsuario(cliente,uidCliente)
    }
    fun cargarClientes(callback: (List<Usuario>) -> Unit){
        return CrudClientes().cargarClientes(callback)
    }
    fun cargarCliente(uidFirebase: String, callback: (Usuario?) -> Unit) {
        CrudClientes().cargarCliente(uidFirebase, callback)
    }
}