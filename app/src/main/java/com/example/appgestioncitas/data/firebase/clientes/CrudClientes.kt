package com.example.appgestioncitas.data.firebase.clientes

import android.util.Log
import com.example.appgestioncitas.models.Usuario
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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
    fun cargarCliente(uidFirebase: String, callback: (Usuario?) -> Unit) {
        val baseDeDatos = FirebaseDatabase.getInstance()
        val referenciaUsuario = baseDeDatos.getReference("usuarios").child(uidFirebase)

        referenciaUsuario.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val usuario = snapshot.getValue(Usuario::class.java)
                callback(usuario)
            }

            override fun onCancelled(error: DatabaseError) {

                callback(null)
            }
        })

    }//añadir que devuelva un cliente
    fun cargarClientes(callback: (List<Usuario>) -> Unit) {
        val baseDeDatos = FirebaseDatabase.getInstance()
        val referenciaUsuarios = baseDeDatos.getReference("usuarios")

        referenciaUsuarios.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val usuarios = mutableListOf<Usuario>()
                for (data in snapshot.children) {
                    val usuario = data.getValue(Usuario::class.java)
                    usuario?.let { usuarios.add(it) }
                }
                callback(usuarios)
            }
            override fun onCancelled(error: DatabaseError) {
                callback(emptyList())
            }
        })
    }

}