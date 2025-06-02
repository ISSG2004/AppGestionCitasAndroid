package com.example.appgestioncitas.models

import java.io.Serializable

data class Cita(
    val id:Any="",
    val fecha_cita:String="",
    var estado:Any="",//disponible, reservada
    var usuario_id:Any="",
    val negocio_id:Any=""
):Serializable
