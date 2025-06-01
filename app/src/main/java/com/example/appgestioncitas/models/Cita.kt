package com.example.appgestioncitas.models

import java.io.Serializable

data class Cita(
    val id:Any="",
    val fecha_cita:String="",
    val estado:Any="",//disponible, reservada
    val usuario_id:Any="",
    val negocio_id:Any=""
):Serializable
