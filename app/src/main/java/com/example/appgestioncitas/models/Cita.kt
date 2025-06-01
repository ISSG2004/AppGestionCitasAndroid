package com.example.appgestioncitas.models

import java.io.Serializable

data class Cita(
    val id:Any="",
    val fecha_cita:Any="",
    val estado:Any="",//disponible, reservada
    val usuario_id:Any="",
    val negocio_id:Any=""
):Serializable
