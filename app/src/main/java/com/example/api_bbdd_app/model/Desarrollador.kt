package com.example.api_bbdd_app.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "desarrolladores")
data class Desarrollador(


    @PrimaryKey(autoGenerate = true) val desarrollador_id: Long,

    @ColumnInfo(name = "nombre_dev") var nombre: String

)