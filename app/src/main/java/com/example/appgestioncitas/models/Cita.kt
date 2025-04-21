package com.example.appgestioncitas.models

data class Cita(
    val id:Int,
    val fecha_cita:String,
    val estado:String,//disponible, reservada
    val usuario_id:Int,
    val negocio_id:Int
)
