package com.example.api_bbdd_app.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "detalles",
    foreignKeys = [
        ForeignKey(
            entity = Juego::class,
            parentColumns = arrayOf("juego_id"),
            childColumns = arrayOf("juego_id"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class Detalle(

    @ColumnInfo(name = "juego_id") var juego_id: Long?,

    @ColumnInfo(name = "descripcion") var descripcion: String,
    @ColumnInfo(name = "requisitos") var requisitos: String,
    @ColumnInfo(name = "precio") var precio: Double

)