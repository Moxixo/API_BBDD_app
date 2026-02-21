package com.example.api_bbdd_app.model

import com.example.api_bbdd_app.data.local.entities.JuegoEntity
import kotlinx.serialization.Serializable

@Serializable
data class Juego(

    var juego_id: Long,
    var nombre: String,
    var genero: String,
    var desarrollador_id: Long,

)

 fun Juego.toEntity(): JuegoEntity {
    return JuegoEntity(
        juego_id =0,
        nombre = this.nombre,
        genero = this.genero,
        desarrollador_id = this.desarrollador_id
    )
}