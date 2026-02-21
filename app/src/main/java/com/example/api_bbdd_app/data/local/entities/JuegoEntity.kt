package com.example.api_bbdd_app.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.api_bbdd_app.model.Juego

//TABLA JUEGOS
@Entity(
    tableName = "juegos",
    foreignKeys = [
        ForeignKey(
            entity = DesarrolladorEntity::class,
            parentColumns = arrayOf("desarrollador_id"), //columna en juegos
            childColumns = arrayOf("desarrollador_id"), //columna en desvs
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
) //Entidad
data class JuegoEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "juego_id") var juego_id: Long = 0,

    @ColumnInfo(name = "desarrollador_id") var desarrollador_id: Long,

    @ColumnInfo(name = "nombre_juego") var nombre: String,
    @ColumnInfo(name = "genero") var genero: String,
)

//RELACIÓN N:M REFERENCIA JUEGOS-PLATAFORMAS
@Entity( //notación room para reconocer la data class como una tabla
    primaryKeys = ["juego_id", "plataforma_id"], //solo mostramos ids
    foreignKeys = [
        ForeignKey(
            entity = JuegoEntity::class,
            parentColumns = ["juego_id"],
            childColumns = ["juego_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class JuegosPlataformasCrossRef(
    val juego_id: Long,
    val plataforma_id: Long,

    )


//CLASE QUE GUARDA juego + desarrollador + detalle + plataformas
data class JuegoCompleto(
    @Embedded val juego: JuegoEntity,
    //dev
    @Relation(
        parentColumn = "desarrollador_id",
        entityColumn = "desarrollador_id"
    )
    val desarrollador: DesarrolladorEntity?,

    // plataformas de tabla intermedia
    @Relation(
        parentColumn = "juego_id",
        entityColumn = "plataforma_id",
        associateBy = Junction(JuegosPlataformasCrossRef::class)
    )
    val plataformas: List<PlataformaEntity>,
    //detalles
    @Relation(
        parentColumn = "juego_id", // ID JuegoEntity
        entityColumn = "juego_id"  // ID foránea en DetalleEntity
    )
    val detalle: DetalleEntity?
)

fun JuegoEntity.toModel() : Juego{
    return Juego(
        juego_id =0,
        nombre = this.nombre,
        genero = this.genero,
        desarrollador_id = this.desarrollador_id
    )
}
