package com.example.appgestioncitas.data.repository

import android.content.Context
import com.example.appgestioncitas.data.firebase.citas.CrudCitas
import com.example.appgestioncitas.models.Cita

class RepositoryCitas {
    fun editarCita(context:Context,cita: Cita) {
        CrudCitas().editarCita(context,cita)
    }
}