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
        ForeignKey( //establecemos foreign key para la relacion 1-N
            entity = DesarrolladorEntity::class,
            parentColumns = arrayOf("desarrollador_id"), //columna en juegos
            childColumns = arrayOf("desarrollador_id"), //columna en devs
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
) //Entidad
data class JuegoEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "juego_id") var juego_id: Long = 0,

    @ColumnInfo(name = "desarrollador_id") var desarrollador_id: Long, //Variable relacional 1-N

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
        ),
        ForeignKey(
            entity = PlataformaEntity::class,
            parentColumns = ["plataforma_id"],
            childColumns = ["plataforma_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
) //tabla referencial
data class JuegosPlataformasCrossRef(
    val juego_id: Long,
    val plataforma_id: Long,

    )


//CLASE QUE GUARDA juego + desarrollador + detalle + plataformas
//Evitamos Joins
data class JuegoCompleto(
    @Embedded val juego: JuegoEntity, //todas las columnas de juegos (tabla base del proyecto)
    //dev
    @Relation( //relacion 1-N
        parentColumn = "desarrollador_id", //columna de juego
        entityColumn = "desarrollador_id" //columna de desarrollador
    )
    val desarrollador: DesarrolladorEntity?, //conecta el juego con el dev
    //operador terciario por si descargamos un juego de la api sin dev

    // plataformas de tabla intermedia
    @Relation( //relacion N-M
        parentColumn = "juego_id", //columna de juego
        entityColumn = "plataforma_id", //columna de plataforma
        associateBy = Junction(JuegosPlataformasCrossRef::class) //join by clase intermedia
    )
    val plataformas: List<PlataformaEntity>, //segun el juego_id, mira el plataforma_id asociado
    //detalles
    @Relation( //relacion 1-1
        parentColumn = "juego_id", // columna del juego
        entityColumn = "juego_id"  // columna del detalle
    )
    val detalle: DetalleEntity? //operador terciario porque la descarga de la api no sabemos si trae detalles o no
)


//función de extensión
fun JuegoEntity.toModel() : Juego{
    return Juego(
        juego_id =0,
        nombre = this.nombre,
        genero = this.genero,
        desarrollador_id = this.desarrollador_id
    )
}
