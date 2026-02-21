package com.example.api_bbdd_app.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.withTransaction
import com.example.api_bbdd_app.data.JuegoRepositoryImpl
import com.example.api_bbdd_app.data.local.AppDatabase
import com.example.api_bbdd_app.data.local.entities.DesarrolladorEntity
import com.example.api_bbdd_app.data.local.entities.DetalleEntity
import com.example.api_bbdd_app.data.local.entities.JuegoCompleto
import com.example.api_bbdd_app.data.local.entities.JuegoEntity
import com.example.api_bbdd_app.data.local.entities.PlataformaEntity
import com.example.api_bbdd_app.data.remote.network.ApiRepository
import com.example.api_bbdd_app.model.Juego
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameViewModel(application: Application) : AndroidViewModel(application) {

    // 1. Instanciamos la BBDD y el DAO
    private val database = AppDatabase.getInstance(application)
    private val dao = database.getJuegoDao()
    private val api = ApiRepository()
    private val repository = JuegoRepositoryImpl(dao, api)


    // carga de devs y plataformas para los selectores
    val desarrolladores: StateFlow<List<DesarrolladorEntity>> = repository.getAllDesarrolladores()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    val plataformas: StateFlow<List<PlataformaEntity>> = repository.getAllPlataformas()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    //Read juego byId -> lectura de bbdd si id ya existe
    fun cargarDatosDelJuego(id: Long, onDatosCargados: (JuegoCompleto) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val juegoCompleto = repository.getJuegoCompletoById(id)
            withContext(Dispatchers.Main) {
                onDatosCargados(juegoCompleto)
            }
        }
    }

    // FUNCIÓN PARA GUARDAR / ACTUALIZAR  -> Create + Update
    fun guardarOActualizarNuevoJuego(
        //si esta guardado el juego, lo actualizamos, sino, lo registramos
        idExistenet: Long?,
        nombre: String,
        genero: String,
        desarrolladorId: Long,
        descripcion: String,
        requisitos: String,
        precio: Double,
        plataformasSeleccionadasIds: List<Long>,
        onSuccess: () -> Unit, // Callback para volver a la pantalla anterior al terminar
    ) {
        // Lanzamos en el hilo de IO porque es base de datos
        viewModelScope.launch(Dispatchers.IO) {
            database.withTransaction {
                if (idExistenet == null) { //si el id es null -> crea nuevo juego
                    // MODO CREAR NUEVO
                    val nuevoJuego = JuegoEntity(
                        nombre = nombre,
                        genero = genero,
                        desarrollador_id = desarrolladorId
                    )
                    val nuevoDetalle = DetalleEntity(
                        juego_id = 0,
                        descripcion = descripcion,
                        requisitos = requisitos,
                        precio = precio
                    )
                    repository.insertJuegoCompleto(
                        nuevoJuego,
                        nuevoDetalle,
                        plataformasSeleccionadasIds
                    )
                } else { //si recibe un ID, carga todos sus datos
                    // MODO ACTUALIZAR
                    val juegoActualizado = JuegoEntity(
                        juego_id = idExistenet,
                        nombre = nombre,
                        genero = genero,
                        desarrollador_id = desarrolladorId
                    )
                    val detalleActualizado = DetalleEntity(
                        juego_id = idExistenet,
                        descripcion = descripcion,
                        requisitos = requisitos,
                        precio = precio
                    )

                    repository.updateJuegoCompleto(
                        juegoActualizado,
                        detalleActualizado,
                        plataformasSeleccionadasIds
                    )
                }
            }
            withContext(Dispatchers.Main) {
                onSuccess()
            }
        }
    }

    fun guardarOActualizarJuegoAPI(
        idExistenet: Long?,
        nombre: String,
        genero: String,
        desarrolladorId: Long,
        onSuccess: () -> Unit,
    ) {

        val juego = Juego(idExistenet, nombre, genero, desarrolladorId)

        viewModelScope.launch {
            repository.postJuego(juego)
            withContext(Dispatchers.Main) {
                onSuccess()

            }


        }
    }
}

