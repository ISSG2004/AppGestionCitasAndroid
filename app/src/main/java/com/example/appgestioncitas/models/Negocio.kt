package com.example.appgestioncitas.models

import java.io.Serializable

data class Negocio(
    val id: String = "",
    val nombre: String = "",
    val password: String = "",
    val tipo_negocio: String = "",
    val direccion: String = "",
    val telefono: String = "",
    val email: String = ""
):Serializable

