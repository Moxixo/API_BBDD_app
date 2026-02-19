package com.example.api_bbdd_app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.api_bbdd_app.data.local.entities.DesarrolladorEntity
import com.example.api_bbdd_app.data.local.entities.DetalleEntity
import com.example.api_bbdd_app.data.local.entities.JuegoEntity
import com.example.api_bbdd_app.data.local.entities.PlataformaEntity
import com.example.api_bbdd_app.data.local.entities.relations.DesarrolladorConJuegos
import com.example.api_bbdd_app.data.local.entities.relations.JuegoConDetalle
import com.example.api_bbdd_app.data.local.entities.relations.JuegoEnPlataforma
import com.example.api_bbdd_app.data.local.entities.relations.JuegosPlataformasCrossRef
import com.example.api_bbdd_app.data.local.entities.relations.PlataformaEnJuego
import kotlinx.coroutines.flow.Flow

@Dao
interface JuegoDao {

    //Suspend fun para usar Corrutinas
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJuego(juegoEntity: JuegoEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetalle(det: DetalleEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDesarrollador(dev: DesarrolladorEntity):Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlataforma(plat: PlataformaEntity):Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGamePlataformaCrossRef(crossRef: JuegosPlataformasCrossRef)

    @Update
    suspend fun updateJuego(juego: JuegoEntity)

    @Delete
    suspend fun deleteJuego(juego: JuegoEntity)

    @Query("SELECT * FROM juegos")
    fun getAllGames(): Flow<List<JuegoEntity>> //actualizaciones reactivas

    @Query("SELECT * FROM desarrolladores")
    fun getAllDesarrolladores(): Flow<List<DesarrolladorEntity>>

    @Query("SELECT * FROM plataformas")
    fun getAllPlataformas(): Flow<List<PlataformaEntity>>

    @Transaction
    @Query("SELECT * FROM juegos WHERE nombre_juego = :nombreJuego")
    fun getJuegoConDetalle(nombreJuego: String): Flow<List<JuegoConDetalle>>

    @Transaction
    @Query("SELECT * FROM desarrolladores WHERE nombre_dev = :devName")
    fun getJuegosConDevs(devName: String): Flow<List<DesarrolladorConJuegos>>

    @Transaction
    @Query("SELECT * FROM plataformas WHERE plataforma_id = :plataforma_id")
    suspend fun getJuegosDePlataforma(plataforma_id: Long): List<PlataformaEnJuego>

    @Transaction
    @Query("SELECT * FROM juegos WHERE nombre_juego = :juego_nombre")
    suspend fun getPlataformasDeJuego(juego_nombre: String): List<JuegoEnPlataforma>

}