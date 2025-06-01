package com.example.appgestioncitas.data.repository

import com.example.appgestioncitas.data.firebase.negocios.CrudNegocios
import com.example.appgestioncitas.models.Negocio

class RepositoryNegocio {
    fun cargarNegocios(callback: (List<Negocio>) -> Unit) {
        CrudNegocios().cargarNegocios { negocios ->
            callback(negocios)

        }
    }
}