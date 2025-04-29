package com.example.appgestioncitas.data.firebase.clientes

import android.util.Log
import com.example.appgestioncitas.models.Usuario
import com.google.firebase.database.FirebaseDatabase

class CrudClientes {
    fun crearUsuario(usuario: Usuario, uidFirebase: String) {
        val baseDeDatos = FirebaseDatabase.getInstance()
        val referenciaUsuarios = baseDeDatos.getReference("usuarios")

        referenciaUsuarios.child(uidFirebase).get().addOnSuccessListener { snapshot ->
            if (!snapshot.exists()) {
                referenciaUsuarios.child(uidFirebase).setValue(usuario)
                    .addOnSuccessListener {
                        Log.d("Firebase", "Usuario creado correctamente.")
                    }
                    .addOnFailureListener {
                        Log.e("Firebase", "Error al crear usuario: ${it.message}")
                    }
            } else {
                Log.d("Firebase", "El usuario ya existe")
            }
        }.addOnFailureListener {
            Log.e("Firebase", "Error al verificar existencia del usuario: ${it.message}")
        }
    }

    fun modificarUsuario(usuario: Usuario, uidFirebase: String) {

    }
    fun cargarCliente(uidFirebase: String) {}//añadir que devuelva un cliente
    fun cargarClientes() {}//añadir que devuelva una mutableList
}