package com.example.api_bbdd_app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.api_bbdd_app.data.local.AppDatabase.Companion.getInstance
import com.example.api_bbdd_app.data.local.dao.JuegoDao
import com.example.api_bbdd_app.data.local.entities.relations.JuegosPlataformasCrossRef
import com.example.api_bbdd_app.data.local.entities.DesarrolladorEntity
import com.example.api_bbdd_app.data.local.entities.DetalleEntity
import com.example.api_bbdd_app.data.local.entities.JuegoEntity
import com.example.api_bbdd_app.data.local.entities.PlataformaEntity
import com.example.api_bbdd_app.model.Desarrollador
import com.example.api_bbdd_app.model.Detalle
import com.example.api_bbdd_app.model.Plataforma
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

private class DatabaseCallback(private val context: Context) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        // Lanzamos una corrutina para insertar los datos en segundo plano
        CoroutineScope(Dispatchers.IO).launch {
            val dao = getInstance(context).getJuegoDao()

            // 1. Precargar Desarrolladores (Ejemplo con 2, tú pones los 6)
            dao.insertDesarrollador(DesarrolladorEntity(1, "Nintendo"))
            dao.insertDesarrollador(DesarrolladorEntity(2, "FromSoftware"))

            // 2. Precargar Plataformas (Ejemplo con 2, tú pones las 5)
            dao.insertPlataforma(PlataformaEntity(1, "Nintendo Switch", "Híbrida", 9))
            dao.insertPlataforma(PlataformaEntity(2, "PS5", "Sobremesa", 9))

            // 3. Precargar Juegos base
            dao.insertJuego(JuegoEntity(100, 1, "Zelda: TOTK", "Aventura"))
            dao.insertDetalle(DetalleEntity(100, "Secuela épica", "12 GB RAM", 69.99))
        }
    }
}


