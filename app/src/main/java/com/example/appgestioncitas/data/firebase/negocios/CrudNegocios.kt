package com.example.appgestioncitas.data.firebase.negocios

import android.util.Log
import com.example.appgestioncitas.models.Negocio
import com.example.appgestioncitas.models.Usuario
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CrudNegocios {
    fun cargarNegocios(callback: (List<Negocio>) -> Unit) {
        val referenciaNegocios = FirebaseDatabase.getInstance().getReference("negocios")
        referenciaNegocios.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listaNegocios = mutableListOf<Negocio>()
                //Log.d("CrudNegocios", "Snapshot recibido: ${snapshot.childrenCount}")

                for (negocioSnap in snapshot.children) {
                    val negocio = negocioSnap.getValue(Negocio::class.java)
                  //  Log.d("CrudNegocios", "Negocio parseado: $negocio")

                    negocio?.let { listaNegocios.add(it) }
                }

                //Log.d("CrudNegocios", "Total negocios parseados: ${listaNegocios.size}")
                callback(listaNegocios)
            }

            override fun onCancelled(error: DatabaseError) {
                //Log.e("CrudNegocios", "Error al leer Firebase: ${error.message}")
                callback(emptyList())
            }

        })
    }
    fun cargarNegocio(negocioId: String, callback: (Negocio?) -> Unit) {
        val referenciaNegocio = FirebaseDatabase.getInstance()
            .getReference("negocios")
            .child(negocioId)

        referenciaNegocio.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val negocio = snapshot.getValue(Negocio::class.java)
                callback(negocio) // Devuelve el negocio si existe, o null si no
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null) // En caso de error
            }
        })
    }

}