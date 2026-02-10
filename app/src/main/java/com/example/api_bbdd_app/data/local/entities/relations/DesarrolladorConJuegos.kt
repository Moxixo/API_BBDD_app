package com.example.api_bbdd_app.data.local.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.api_bbdd_app.data.local.entities.Desarrollador
import com.example.api_bbdd_app.model.Juego

data class DesarrolladorConJuegos(
    @Embedded val dev : Desarrollador,
    @Relation(
        parentColumn = "juego_id",
        entityColumn = "juego_id"
    ) val juegos : List<Juego>


)