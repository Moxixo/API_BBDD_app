package com.example.api_bbdd_app.data

import com.example.api_bbdd_app.data.local.AppDatabase
import com.example.api_bbdd_app.data.local.entities.DesarrolladorEntity
import com.example.api_bbdd_app.data.local.entities.DetalleEntity
import com.example.api_bbdd_app.data.local.entities.JuegoEntity
import com.example.api_bbdd_app.data.local.entities.PlataformaEntity
import com.example.api_bbdd_app.data.local.entities.relations.JuegoEnPlataforma
import com.example.api_bbdd_app.data.local.entities.relations.JuegosPlataformasCrossRef
import com.example.api_bbdd_app.data.local.entities.relations.PlataformaEnJuego
import kotlinx.coroutines.flow.Flow

/**Clase repositorio
* -> acceso a los datos (bbdd,api)
* -> para conectar con la logica
* que está a parte**/

interface JuegoRepository {

    //Aquí van las operaciones CRUD:
    suspend fun insertJuego(juego: JuegoEntity): Long

    suspend fun insertDetalle(det : DetalleEntity)

    suspend fun insertDesarrollador(dev: DesarrolladorEntity): Long

    suspend fun insertPlataforma(plat: PlataformaEntity): Long

    suspend fun insertGamePlataformaCrossRef(crossRef: JuegosPlataformasCrossRef)

    suspend fun updateJuego(juego: JuegoEntity)

    suspend fun deleteJuego(juego: JuegoEntity)

    fun getAllGames(): Flow<List<JuegoEntity>>

    fun getAllDesarrolladores(): Flow<List<DesarrolladorEntity>>

    fun getAllPlataformas(): Flow<List<PlataformaEntity>>

    suspend fun getJuegosDePlataforma(plataformaId: Long): List<PlataformaEnJuego>

    suspend fun getPlataformasDeJuego(juegoNombre: String): List<JuegoEnPlataforma>
}