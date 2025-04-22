package com.example.appgestioncitas.data.firebase.clientes

import android.util.Log
import com.example.appgestioncitas.models.Usuario
import com.google.firebase.database.FirebaseDatabase

class CrudClientes {
    fun crearUsuario(usuario: Usuario, uidFirebase: String) {
        // Lógica para crear un usuario en la base de datos de firebase
        val baseDeDatos = FirebaseDatabase.getInstance()
        val referenciaUsuarios = baseDeDatos.getReference("usuarios")
        val nuevoUsuario = Usuario(uidFirebase, "", "", "", "")
        // referenciaUsuarios.child(uidFirebase).setValue(nuevoUsuario)
        //validamos con un get y con un snapshot si el usuario existe
        referenciaUsuarios.child(uidFirebase).get().addOnSuccessListener { snapshot ->
            if (!snapshot.exists()) {
                referenciaUsuarios.child(uidFirebase).setValue(nuevoUsuario)
            } else {
                Log.d("Firebase", "El usuario ya existe")
            }
        }
    }
    fun modificarUsuario(usuario: Usuario, uidFirebase: String) {

    }
    fun cargarCliente(uidFirebase: String) {}//añadir que devuelva una mutableList
    fun cargarClientes() {}//añadir que devuelva una mutableList
}