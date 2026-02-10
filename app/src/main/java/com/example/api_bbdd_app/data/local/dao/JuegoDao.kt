package com.example.api_bbdd_app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.api_bbdd_app.data.local.entities.Detalle
import com.example.api_bbdd_app.model.Juego
import kotlinx.coroutines.flow.Flow

@Dao
interface JuegoDao{

    @Insert
    suspend fun insertGame(juego: Juego)

    @Insert
    suspend fun insertDetalle(det : Detalle)

    @Query("SELECT * FROM juegos")
    suspend fun getAllGames() : Flow<List<Juego>> //actualizaciones reactivas



}