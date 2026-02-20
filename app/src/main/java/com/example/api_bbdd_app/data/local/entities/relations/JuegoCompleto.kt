package com.example.api_bbdd_app.data.local.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.api_bbdd_app.data.local.entities.DesarrolladorEntity
import com.example.api_bbdd_app.data.local.entities.DetalleEntity
import com.example.api_bbdd_app.data.local.entities.JuegoEntity
import com.example.api_bbdd_app.data.local.entities.PlataformaEntity


data class JuegoCompleto(
    @Embedded val juego: JuegoEntity,
    //dev
    @Relation(
        parentColumn = "desarrollador_id",
        entityColumn = "desarrollador_id"
    )
    val desarrollador: DesarrolladorEntity,

    // plataformas de tabla intermedia
    @Relation(
        parentColumn = "juego_id", // O el nombre exacto que le pusiste a la ID de tu JuegoEntity
        entityColumn = "plataforma_id",
        associateBy = Junction(JuegosPlataformasCrossRef::class)
    )
    val plataformas: List<PlataformaEntity>,
    //detalles
    @Relation(
        parentColumn = "juego_id", // ID JuegoEntity
        entityColumn = "juego_id"  // ID foránea en DetalleEntity
    )
    val detalle: DetalleEntity
)
