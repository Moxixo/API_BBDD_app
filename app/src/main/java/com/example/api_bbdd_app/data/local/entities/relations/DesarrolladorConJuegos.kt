package com.example.api_bbdd_app.data.local.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.api_bbdd_app.data.local.entities.DesarrolladorEntity
import com.example.api_bbdd_app.data.local.entities.JuegoEntity

data class DesarrolladorConJuegos(
    @Embedded val dev : DesarrolladorEntity,
    @Relation(
        parentColumn = "desarrollador_id",
        entityColumn = "desarrollador_id"
    )
    val juegoEntities : List<JuegoEntity>


)