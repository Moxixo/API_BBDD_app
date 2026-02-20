package com.example.api_bbdd_app.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.withTransaction
import com.example.api_bbdd_app.data.JuegoRepositoryImpl
import com.example.api_bbdd_app.data.local.AppDatabase
import com.example.api_bbdd_app.data.local.entities.DesarrolladorEntity
import com.example.api_bbdd_app.data.local.entities.DetalleEntity
import com.example.api_bbdd_app.data.local.entities.JuegoEntity
import com.example.api_bbdd_app.data.local.entities.PlataformaEntity
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
    private val repository = JuegoRepositoryImpl(dao)

    // 2. OBSERVABLES PARA LA UI (Cargan automáticamente devs y plataformas para los selectores)
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

    // FUNCIÓN PARA GUARDAR
    fun guardarNuevoJuego(
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

            // insertar el juego y guardar ID
            val nuevoJuegoId =
                JuegoEntity(
                    nombre = nombre,
                    genero = genero,
                    desarrollador_id = desarrolladorId
                )


            // usar id guardado para el detalle
            val nuevoDetalle = DetalleEntity(
                juego_id = 0,
                descripcion = descripcion,
                requisitos = requisitos,
                precio = precio
            )
            // inserción  atómica
            database.withTransaction {
                repository.insertJuegoCompleto(
                    nuevoJuegoId,
                    nuevoDetalle,
                    plataformasSeleccionadasIds
                )

            }

            // avisamos a la interfaz
            withContext(Dispatchers.Main) {
                onSuccess()
            }
        }
    }
}

