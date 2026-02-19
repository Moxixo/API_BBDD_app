package com.example.api_bbdd_app.data.local.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.api_bbdd_app.data.local.entities.JuegoEntity
import com.example.api_bbdd_app.data.local.entities.PlataformaEntity

/*
* RELACIÓN N:M
*/
data class PlataformaEnJuego(
    @Embedded val plataformaEntity: PlataformaEntity,
    @Relation(
        parentColumn = "plataforma_id",
        entityColumn = "juego_id",
        associateBy = Junction(JuegosPlataformasCrossRef::class)
    )
    val juegoEntities : List<JuegoEntity>
)