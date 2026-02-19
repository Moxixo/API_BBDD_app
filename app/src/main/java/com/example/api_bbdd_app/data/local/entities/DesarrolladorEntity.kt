package com.example.api_bbdd_app.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "desarrolladores")
data class DesarrolladorEntity(

    @PrimaryKey(autoGenerate = true) val desarrollador_id: Long = 0,

    @ColumnInfo(name = "nombre_dev") var nombre: String,

    )