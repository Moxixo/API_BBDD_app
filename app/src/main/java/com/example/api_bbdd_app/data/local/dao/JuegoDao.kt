package com.example.api_bbdd_app.data.local.dao

import android.widget.ListPopupWindow
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.api_bbdd_app.data.local.entities.Detalle
import com.example.api_bbdd_app.data.local.entities.relations.DesarrolladorConJuegos
import com.example.api_bbdd_app.data.local.entities.relations.JuegoConDetalle
import com.example.api_bbdd_app.model.Desarrollador
import com.example.api_bbdd_app.model.Juego
import kotlinx.coroutines.flow.Flow

@Dao
interface JuegoDao{

    @Insert
    suspend fun insertGame(juego: Juego)

    @Insert
    suspend fun insertDetalle(det : Detalle)

    @Insert
    suspend fun insertDesarrollador(dev : Desarrollador)

    @Query("SELECT * FROM juegos")
    suspend fun getAllGames() : Flow<List<Juego>> //actualizaciones reactivas

    @Query("SELECT * FROM juegos WHERE nombre_juego = :nombreJuego")
    suspend fun getJuegoConDetalle(nombreJuego : String): Flow<List<JuegoConDetalle>>

    @Query("SELECT * FROM desarrolladores WHERE nombre_dev = :devName")
    suspend fun getJuegosConDevs(devName : String) : Flow<List<DesarrolladorConJuegos>>


}