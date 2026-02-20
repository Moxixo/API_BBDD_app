package com.example.api_bbdd_app.data.local.entities.relations

import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.api_bbdd_app.data.local.entities.JuegoEntity

@Entity(primaryKeys = ["juego_id","plataforma_id"],
    foreignKeys = [
        ForeignKey(
            entity = JuegoEntity::class,
            parentColumns = ["juego_id"],
            childColumns = ["juego_id"],
            onDelete = ForeignKey.CASCADE
        )
    ])
data class JuegosPlataformasCrossRef (
    val juego_id: Long,
    val plataforma_id:Long

)