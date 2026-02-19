package com.example.api_bbdd_app.model

import kotlinx.serialization.Serializable

@Serializable
data class Juego(

    var juego_id: Long?,
    var nombre: String,
    var genero: String,
    var desarrollador_id: Long,

)