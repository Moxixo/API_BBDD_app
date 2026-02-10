package com.example.api_bbdd_app.data.local.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.api_bbdd_app.data.local.entities.Detalle
import com.example.api_bbdd_app.data.local.entities.Juego

/*
* RELACIÓN 1:1
*/
data class JuegoConDetalle(
    @Embedded val juego : Juego,
    @Relation(
        parentColumn = "juego_id",
        entityColumn = "juego_id")
    val detalle : Detalle
)