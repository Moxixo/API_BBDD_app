package com.example.api_bbdd_app.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "juegos",
    foreignKeys = [
        ForeignKey(
            entity = Desarrollador::class,
            parentColumns = arrayOf("desarrollador_id"),
            childColumns = arrayOf("desarrollador_id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Juego(
    @PrimaryKey(autoGenerate = true) var juego_id: Long?,

    @ColumnInfo(name = "desarrollador_id") var desarrollador_id: Long,

    @ColumnInfo(name = "nombre_juego") var nombre: String,
    @ColumnInfo(name = "genero") var genero: String,
)