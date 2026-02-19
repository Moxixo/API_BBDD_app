package com.example.api_bbdd_app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.api_bbdd_app.data.local.dao.JuegoDao
import com.example.api_bbdd_app.data.local.entities.relations.JuegosPlataformasCrossRef
import com.example.api_bbdd_app.data.local.entities.DesarrolladorEntity
import com.example.api_bbdd_app.data.local.entities.DetalleEntity
import com.example.api_bbdd_app.data.local.entities.JuegoEntity
import com.example.api_bbdd_app.data.local.entities.PlataformaEntity

@Database(
    entities = [
        JuegoEntity::class,
        DesarrolladorEntity::class,
        DetalleEntity::class,
        PlataformaEntity::class,
        JuegosPlataformasCrossRef::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getJuegoDao(): JuegoDao

    //Singleton -> metodo creación database
    companion object {
        @Volatile //Necesario si hay corrutinas -> evita copias de la variable INSTANCE
        //así si un hilo realiza cambios en la bbdd, los demás se enteran
        private var INSTANCE: AppDatabase? = null

        //Context conecta el entorno de la app con la persistencia de la bbdd
        fun getInstance(context: Context): AppDatabase {
            synchronized(this) { //controlar que vaya un hilo detrás de otro
                return INSTANCE
                    ?: Room.databaseBuilder( //si la bbdd esta creada -> la devuelve,sino implementa Room
                        context.applicationContext,
                        AppDatabase::class.java,
                        "games_db" // <--- nombre en la web
                    )
                        .build()
                        .also {//it = bbdd creada con Room.dbBuilder
                            INSTANCE = it //guardaa en la variable INSTANCE != null
                        }
            }
        }
    }
}




