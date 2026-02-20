package com.example.api_bbdd_app.data.local.entities.relations

import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.ForeignKey
import androidx.room.Relation
import com.example.api_bbdd_app.data.local.entities.DetalleEntity
import com.example.api_bbdd_app.data.local.entities.JuegoEntity

/*
* RELACIÓN 1:1
*/
data class JuegoConDetalle(
    @Embedded val juegoEntity : JuegoEntity,
    @Relation(
        parentColumn = "juego_id",
        entityColumn = "juego_id")
    val detalleEntity : DetalleEntity
)