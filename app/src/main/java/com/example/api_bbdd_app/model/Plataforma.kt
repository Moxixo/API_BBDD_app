package com.example.api_bbdd_app.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plataformas")
data class Plataforma(

    @PrimaryKey(autoGenerate = true) var plataforma_id:Long?,
    @ColumnInfo(name = "nombre_plataforma") var nombre:String,
    @ColumnInfo(name = "tipo")var tipo:String,
    @ColumnInfo(name = "generacion")var generacion: Int
)