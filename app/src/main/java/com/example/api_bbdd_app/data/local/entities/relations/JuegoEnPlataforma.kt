package com.example.api_bbdd_app.data.local.entities.relations

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.api_bbdd_app.model.Juego
import com.example.api_bbdd_app.model.Plataforma

@Entity(
    tableName = "juegoYplataforma",
    foreignKeys = [
        ForeignKey(
            entity = Juego::class,
            parentColumns = arrayOf("juego_id"),
            childColumns = arrayOf("juego_id"),
            onDelete = ForeignKey.Companion.CASCADE,
            onUpdate = ForeignKey.Companion.CASCADE,

            ),
        ForeignKey(
            entity = Plataforma::class,
            parentColumns = arrayOf("plataforma_id"),
            childColumns = arrayOf("plataforma_id"),
            onDelete = ForeignKey.Companion.CASCADE,
            onUpdate = ForeignKey.Companion.CASCADE,

            ),
    ]
)
data class JuegoEnPlataforma(
    @PrimaryKey(false) val juego_id: Long,
    @PrimaryKey(false) val plataforma_id: Long,
)