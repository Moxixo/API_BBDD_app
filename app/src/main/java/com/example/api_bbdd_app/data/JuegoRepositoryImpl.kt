package com.example.api_bbdd_app.data

import com.example.api_bbdd_app.data.local.dao.JuegoDao
import com.example.api_bbdd_app.data.local.entities.DesarrolladorEntity
import com.example.api_bbdd_app.data.local.entities.DetalleEntity
import com.example.api_bbdd_app.data.local.entities.JuegoEntity
import com.example.api_bbdd_app.data.local.entities.PlataformaEntity
import com.example.api_bbdd_app.data.local.entities.JuegoCompleto
import com.example.api_bbdd_app.data.local.entities.JuegosPlataformasCrossRef
import kotlinx.coroutines.flow.Flow

class JuegoRepositoryImpl(
    private val room: JuegoDao,
) : JuegoRepository {
    //CREATE -> funciona
    override suspend fun insertJuegoCompleto(
        juego: JuegoEntity,
        detalle: DetalleEntity,
        plataformasIds: List<Long>,
    ) {
        val juegoId =
            room.insertJuego(juego) //llama al metodo deabajo insertJuego -> se manda al repositry

        val detalleConId = detalle.copy(juego_id = juegoId)
        room.insertDetalle(detalleConId)

        //tabla intermedia plataforma-juego
        plataformasIds.forEach { platId ->
            room.insertGamePlataformaCrossRef(JuegosPlataformasCrossRef(juegoId, platId))
        }
    }

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

    //UPDATE
    override suspend fun updateJuego(juego: JuegoEntity) {
        room.updateJuego(juego)
    }

    override suspend fun updateJuegoCompleto(
        juego: JuegoEntity,
        detalle: DetalleEntity,
        plataformasIds: List<Long>,
    ) {
        room.updateJuego(juego)
        room.updateDetalle(detalle)

        room.deletePlataformasDeJuego(juego.juego_id)
        plataformasIds.forEach { platId ->
            room.insertGamePlataformaCrossRef(JuegosPlataformasCrossRef(juego.juego_id, platId))
        }
    }

    //DELETE -> funciona
    override suspend fun deleteJuego(juego: JuegoEntity) {
        room.deleteJuego(juego)
    }

    //READ -> funciona
    override suspend fun getJuegoCompletoById(id: Long): JuegoCompleto {
        return room.getJuegoCompletoById(id)
    }

    override fun getJuegosCompletos(): Flow<List<JuegoCompleto>> {
        return room.getJuegosCompletos()
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

}