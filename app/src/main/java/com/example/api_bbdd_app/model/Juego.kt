package com.example.api_bbdd_app.model

import kotlinx.serialization.Serializable

@Serializable
data class Juego(

    var id: Long?,
    var nombre: String,
    var precio: String,
    var fechaSalida: String,

)