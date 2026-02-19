package com.example.api_bbdd_app.data

import com.example.api_bbdd_app.data.local.AppDatabase
import com.example.api_bbdd_app.data.local.dao.JuegoDao
import com.example.api_bbdd_app.data.local.entities.DesarrolladorEntity
import com.example.api_bbdd_app.data.local.entities.DetalleEntity
import com.example.api_bbdd_app.data.local.entities.JuegoEntity
import com.example.api_bbdd_app.data.local.entities.PlataformaEntity
import com.example.api_bbdd_app.data.local.entities.relations.JuegoEnPlataforma
import com.example.api_bbdd_app.data.local.entities.relations.JuegosPlataformasCrossRef
import com.example.api_bbdd_app.data.local.entities.relations.PlataformaEnJuego
import kotlinx.coroutines.flow.Flow

class JuegoRepositoryImpl(
    private val room : JuegoDao
) : JuegoRepository {
    override suspend fun insertJuego(juego: JuegoEntity): Long {
        return room.insertJuego(juego)
    }

    override suspend fun insertDetalle(det: DetalleEntity) {
        room.insertDetalle(det)
    }

    override suspend fun insertDesarrollador(dev: DesarrolladorEntity): Long {
        return room.insertDesarrollador(dev)
    }

    override suspend fun insertPlataforma(plat: PlataformaEntity): Long {
        return room.insertPlataforma(plat)
    }

    override suspend fun insertGamePlataformaCrossRef(crossRef: JuegosPlataformasCrossRef) {
       room.insertGamePlataformaCrossRef(crossRef)
    }

    override suspend fun updateJuego(juego: JuegoEntity) {
        room.updateJuego(juego)
    }

    override suspend fun deleteJuego(juego: JuegoEntity) {
       room.deleteJuego(juego)
    }

    override fun getAllGames(): Flow<List<JuegoEntity>> {
       return room.getAllGames()
    }

    override fun getAllDesarrolladores(): Flow<List<DesarrolladorEntity>> {
       return room.getAllDesarrolladores()
    }

    override fun getAllPlataformas(): Flow<List<PlataformaEntity>> {
        return room.getAllPlataformas()
    }

    override suspend fun getJuegosDePlataforma(plataformaId: Long): List<PlataformaEnJuego> {
       return room.getJuegosDePlataforma(plataformaId)
    }

    override suspend fun getPlataformasDeJuego(juegoNombre: String): List<JuegoEnPlataforma> {
        return room.getPlataformasDeJuego(juegoNombre)
    }

}