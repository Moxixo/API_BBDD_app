package com.example.api_bbdd_app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.api_bbdd_app.model.Desarrollador
import com.example.api_bbdd_app.model.Detalle
import com.example.api_bbdd_app.model.Juego
import com.example.api_bbdd_app.model.Plataforma

@Database(
    entities = [
        Juego::class,
        Desarrollador::class,
        Detalle::class,
        Plataforma::class],
    version = 1,
    exportSchema = true)
abstract class AppDatabase : RoomDatabase(){

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "note_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }




}