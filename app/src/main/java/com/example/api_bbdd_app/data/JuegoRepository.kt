package com.example.api_bbdd_app.data

import com.example.api_bbdd_app.data.local.entities.DesarrolladorEntity
import com.example.api_bbdd_app.data.local.entities.DetalleEntity
import com.example.api_bbdd_app.data.local.entities.JuegoEntity
import com.example.api_bbdd_app.data.local.entities.PlataformaEntity
import com.example.api_bbdd_app.data.local.entities.relations.JuegoCompleto
import com.example.api_bbdd_app.data.local.entities.relations.JuegosPlataformasCrossRef
import kotlinx.coroutines.flow.Flow

/**Clase repositorio
 * -> acceso a los datos (bbdd,api)
 * -> para conectar con la logica
 * que está a parte**/

interface JuegoRepository {

    //Aquí van las operaciones CRUD:

    suspend fun insertJuegoCompleto(
        juego: JuegoEntity,
        detalle: DetalleEntity,
        plataformasIds: List<Long>,
    )

    //----INSERTS---
    //JUEGO -> necesario para insertCompleto
    suspend fun insertJuego(juego: JuegoEntity): Long
    //DETALLE -> necesario para insertCompleto
    suspend fun insertDetalle(det: DetalleEntity)
    //DEV -> necesario para insertCompleto
    suspend fun insertDesarrollador(dev: DesarrolladorEntity): Long
    //PLATAFORMA -> necesario para insertCompleto
    suspend fun insertPlataforma(plat: PlataformaEntity): Long
    //REFERENCIA entre TABLAS -> necesario para insertCompleto
    suspend fun insertGamePlataformaCrossRef(crossRef: JuegosPlataformasCrossRef)

    //----UPDATE----
    suspend fun updateJuego(juego: JuegoEntity)

    suspend fun updateJuegoCompleto(
        juego: JuegoEntity,
        detalle: DetalleEntity,
        plataformasIds: List<Long>
    )
    //----DELETE----
    suspend fun deleteJuego(juego: JuegoEntity)

    //----READ----
    suspend fun getJuegoCompletoById(id: Long): JuegoCompleto
    fun getJuegosCompletos(): Flow<List<JuegoCompleto>>

    fun getAllGames(): Flow<List<JuegoEntity>>

    fun getAllDesarrolladores(): Flow<List<DesarrolladorEntity>>

    fun getAllPlataformas(): Flow<List<PlataformaEntity>>

}