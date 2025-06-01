package com.example.appgestioncitas.models

data class Cita(
    val id:String="",
    val fecha_cita:String="",
    val estado:String="",//disponible, reservada
    val usuario_id:String="",
    val negocio_id:String=""
)
