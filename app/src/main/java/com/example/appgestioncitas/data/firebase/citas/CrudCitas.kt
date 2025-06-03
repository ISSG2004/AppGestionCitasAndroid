package com.example.appgestioncitas.data.firebase.citas

import android.content.Context
import android.widget.Toast
import com.example.appgestioncitas.models.Cita
import com.example.appgestioncitas.models.Usuario
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CrudCitas {
    fun cargarCita(uidFirebase: String, callback: (Cita?) -> Unit) {
        val baseDeDatos = FirebaseDatabase.getInstance()
        val referenciaCitas = baseDeDatos.getReference("citas").child(uidFirebase)

        referenciaCitas.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val cita = snapshot.getValue(Cita::class.java)
                callback(cita)
            }

            override fun onCancelled(error: DatabaseError) {

                callback(null)
            }
        })

    }
    fun cargarCitas(callback: (List<Cita>) -> Unit): ValueEventListener {
        val baseDeDatos = FirebaseDatabase.getInstance()
        val referenciaCitas = baseDeDatos.getReference("citas")

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val citas = mutableListOf<Cita>()
                for (data in snapshot.children) {
                    val cita = data.getValue(Cita::class.java)
                    cita?.let { citas.add(it) }
                }
                callback(citas)
            }
            override fun onCancelled(error: DatabaseError) {
                callback(emptyList())
            }
        }
        referenciaCitas.addValueEventListener(listener)
        return listener
    }

    fun editarCita(context:Context , cita: Cita) {
        val baseDeDatos = FirebaseDatabase.getInstance()
        val referenciaCitas = baseDeDatos.getReference("citas")

        // Asegúrate de que cita.id contenga el ID del nodo que quieres editar
        val citaRef = referenciaCitas.child(cita.id.toString())

        // Solo actualizamos el campo "estado"
        val actualizacion = mapOf<String, Any>(
            "estado" to cita.estado,
            "id_usuario" to cita.usuario_id
        )

        citaRef.updateChildren(actualizacion)
            .addOnSuccessListener {

            }
            .addOnFailureListener { error ->

            }
    }

}