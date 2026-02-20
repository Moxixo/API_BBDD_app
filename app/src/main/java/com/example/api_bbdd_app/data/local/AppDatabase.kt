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
                    ).addCallback(DatabaseCallback(context))
                        .build()
                        .also {//it = bbdd creada con Room.dbBuilder
                            INSTANCE = it //guardaa en la variable INSTANCE != null
                        }
            }
        }
    }
}

//Funcion de precarga de datos en la bbdd fijos
private class DatabaseCallback(private val context: Context) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        // Lanzamos una corrutina para insertar los datos en segundo plano
        CoroutineScope(Dispatchers.IO).launch {
            val dao = getInstance(context).getJuegoDao()

            //Desarrolladores fijos
            val idNintendo = dao.insertDesarrollador(DesarrolladorEntity(nombre = "Nintendo"))
            val idAtlus = dao.insertDesarrollador(DesarrolladorEntity(nombre = "Atlus"))
            val idCapcom = dao.insertDesarrollador(DesarrolladorEntity(nombre = "Capcom"))
            val idTeamCherry = dao.insertDesarrollador(DesarrolladorEntity(nombre = "TeamCherry"))
            val idBethesda = dao.insertDesarrollador(DesarrolladorEntity(nombre = "Bethesda"))
            ///Añadimos plataformas fijas
            val idPc = dao.insertPlataforma(PlataformaEntity(nombre = "PC", tipo = "Sobremesa", generacion = 0))
            val idPlayStation = dao.insertPlataforma(PlataformaEntity(nombre = "PlayStation5", tipo = "Sobremesa", generacion = 9))
            val idXbox = dao.insertPlataforma(PlataformaEntity(nombre = "Xbox360", tipo = "Sobremesa", generacion = 9))
            val idSwitch = dao.insertPlataforma(PlataformaEntity(nombre = "Switch", tipo = "Híbrida", generacion = 8))
            //Juegos base en la bbdd
            val idJuego = dao.insertJuego(JuegoEntity(nombre = "Hollow Knight", genero = "Metroidvania", desarrollador_id = idTeamCherry))
            val idJ = dao.insertJuego(JuegoEntity(nombre= "Persona 3 reload", genero = "JRPG, Aventura", desarrollador_id = idAtlus))
            //Tabla de referencia relacion N-M
            dao.insertGamePlataformaCrossRef(JuegosPlataformasCrossRef(idJuego, idPc))
            dao.insertGamePlataformaCrossRef(JuegosPlataformasCrossRef(idJuego, idSwitch))

            dao.insertGamePlataformaCrossRef(JuegosPlataformasCrossRef(idJ,idSwitch))
            dao.insertGamePlataformaCrossRef(JuegosPlataformasCrossRef(idJ,idPlayStation))

        }
    }
}


