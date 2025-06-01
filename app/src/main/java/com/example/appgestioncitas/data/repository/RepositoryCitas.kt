package com.example.appgestioncitas.data.repository

import android.content.Context
import com.example.appgestioncitas.data.firebase.citas.CrudCitas
import com.example.appgestioncitas.data.firebase.negocios.CrudNegocios
import com.example.appgestioncitas.models.Cita
import com.example.appgestioncitas.models.Negocio

class RepositoryCitas {
    fun editarCita(context:Context,cita: Cita) {
        CrudCitas().editarCita(context,cita)
    }
    fun cargarCitas(callback: (List<Cita>) -> Unit) {
        CrudCitas().cargarCitas { citas ->
            callback(citas)

        }
    }
}