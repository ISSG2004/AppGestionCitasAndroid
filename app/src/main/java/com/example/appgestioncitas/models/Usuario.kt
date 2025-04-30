package com.example.appgestioncitas.models

data class Usuario(
    var id: String? = null,
    var userName: String? = null,
    var correo: String? = null,
    var telefono: String? = null,
    var password: String? = null
) {
    // Constructor vacío requerido por Firebase
    constructor() : this(null, null, null, null, null)
}
