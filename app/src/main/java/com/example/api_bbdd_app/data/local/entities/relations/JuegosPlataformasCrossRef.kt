package com.example.api_bbdd_app.data.local.entities.relations

import androidx.room.Entity

@Entity(primaryKeys = ["juego_id","plataforma_id"])
data class JuegosPlataformasCrossRef (
    val juego_id: Long,
    val plataforma_id:Long

)