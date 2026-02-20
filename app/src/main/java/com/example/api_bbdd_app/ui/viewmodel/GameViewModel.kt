package com.example.api_bbdd_app.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.withTransaction
import com.example.api_bbdd_app.data.local.AppDatabase
import com.example.api_bbdd_app.data.local.entities.DesarrolladorEntity
import com.example.api_bbdd_app.data.local.entities.DetalleEntity
import com.example.api_bbdd_app.data.local.entities.JuegoEntity
import com.example.api_bbdd_app.data.local.entities.PlataformaEntity
import com.example.api_bbdd_app.data.local.entities.relations.JuegosPlataformasCrossRef
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

    // 2. OBSERVABLES PARA LA UI (Cargan automáticamente devs y plataformas para los selectores)
    val desarrolladores: StateFlow<List<DesarrolladorEntity>> = dao.getAllDesarrolladores()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val plataformas: StateFlow<List<PlataformaEntity>> = dao.getAllPlataformas()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // 3. LA FUNCIÓN PARA GUARDAR (Usando tu estilo paso a paso con transaction)
    fun guardarNuevoJuego(
        nombre: String,
        genero: String,
        desarrolladorId: Long,
        descripcion: String,
        requisitos: String,
        precio: Double,
        plataformasSeleccionadasIds: List<Long>,
        onSuccess: () -> Unit // Callback para volver a la pantalla anterior al terminar
    ) {
        // Lanzamos en el hilo de IO porque es base de datos
        viewModelScope.launch(Dispatchers.IO) {

            // inserción  atómica
            database.withTransaction {
                // insertar el juego y guardar ID
                val nuevoJuegoId = dao.insertJuego(
                    JuegoEntity(
                        nombre = nombre,
                        genero = genero,
                        desarrollador_id = desarrolladorId
                    )
                )

                // usar id guardado para el detalle
                dao.insertDetalle(
                    DetalleEntity(
                        juego_id = nuevoJuegoId,
                        descripcion = descripcion,
                        requisitos = requisitos,
                        precio = precio
                    )
                )

                // buscar en la lista de plataformas
                plataformasSeleccionadasIds.forEach { platId ->
                    dao.insertGamePlataformaCrossRef(
                        JuegosPlataformasCrossRef(juego_id = nuevoJuegoId, plataforma_id = platId)
                    )
                }
            }

            // avisamos a la interfaz
            withContext(Dispatchers.Main) {
                onSuccess()
            }
        }
    }
}

