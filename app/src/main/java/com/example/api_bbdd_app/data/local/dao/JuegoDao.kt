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
import com.example.api_bbdd_app.data.local.entities.JuegoCompleto
import com.example.api_bbdd_app.data.local.entities.JuegoEntity
import com.example.api_bbdd_app.data.local.entities.JuegosPlataformasCrossRef
import com.example.api_bbdd_app.data.local.entities.PlataformaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JuegoDao {
    @Transaction
    suspend fun insertJuegoCompleto(
        juego: JuegoEntity,
        detalle: DetalleEntity,
        plataformasIds: List<Long>,
    ) {
        val juegoId =
            insertJuego(juego) //llama al metodo deabajo insertJuego -> se manda al repositry

        val detalleConId = detalle.copy(juego_id = juegoId)
        insertDetalle(detalleConId)

        //tabla intermedia plataforma-juego
        plataformasIds.forEach { platId ->
            insertGamePlataformaCrossRef(JuegosPlataformasCrossRef(juegoId, platId))
        }
    }

    //Suspend fun para usar Corrutinas
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertJuego(juegoEntity: JuegoEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetalle(det: DetalleEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDesarrollador(dev: DesarrolladorEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlataforma(plat: PlataformaEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGamePlataformaCrossRef(crossRef: JuegosPlataformasCrossRef)

    @Update
    suspend fun updateJuego(juego: JuegoEntity)

    @Update
    suspend fun updateDetalle(det: DetalleEntity)

    @Delete
    suspend fun deleteJuego(juego: JuegoEntity)

    @Query("DELETE FROM juegosplataformascrossref WHERE juego_id = :juegoId")
    suspend fun deletePlataformasDeJuego(juegoId: Long)

    @Transaction
    @Query("SELECT * FROM juegos WHERE juego_id = :id") // Asegúrate de que el nombre de tu clave primaria sea 'id' en JuegoEntity
    suspend fun getJuegoCompletoById(id: Long): JuegoCompleto

    @Transaction
    @Query("SELECT * FROM juegos")
    fun getJuegosCompletos(): Flow<List<JuegoCompleto>>

    @Query("SELECT * FROM juegos")
    fun getAllGames(): Flow<List<JuegoEntity>> //actualizaciones reactivas

    @Query("SELECT * FROM desarrolladores")
    fun getAllDesarrolladores(): Flow<List<DesarrolladorEntity>>

    @Query("SELECT * FROM plataformas")
    fun getAllPlataformas(): Flow<List<PlataformaEntity>>

}