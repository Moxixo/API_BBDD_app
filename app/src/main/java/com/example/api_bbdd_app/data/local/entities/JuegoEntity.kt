package com.example.api_bbdd_app.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "juegos",
    foreignKeys = [
        ForeignKey(
            entity = DesarrolladorEntity::class,
            parentColumns = arrayOf("desarrollador_id"),
            childColumns = arrayOf("desarrollador_id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class JuegoEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "juego_id") var juego_id: Long? =0,

    @ColumnInfo(name = "desarrollador_id") var desarrollador_id: Long,

    @ColumnInfo(name = "nombre_juego") var nombre: String,
    @ColumnInfo(name = "genero") var genero: String,
)