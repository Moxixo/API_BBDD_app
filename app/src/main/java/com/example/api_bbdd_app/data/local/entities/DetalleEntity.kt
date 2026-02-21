package com.example.api_bbdd_app.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "detalles",
    foreignKeys = [
        ForeignKey( //Relacion 1-1, juego id es PK y FK a la vez
            entity = JuegoEntity::class,
            parentColumns = arrayOf("juego_id"),
            childColumns = arrayOf("juego_id"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class DetalleEntity(

    @PrimaryKey @ColumnInfo(name = "juego_id") var juego_id: Long,

    @ColumnInfo(name = "descripcion") var descripcion: String,
    @ColumnInfo(name = "requisitos") var requisitos: String,
    @ColumnInfo(name = "precio") var precio: Double,

    )