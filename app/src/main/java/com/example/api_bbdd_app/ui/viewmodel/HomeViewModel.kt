package com.example.api_bbdd_app.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_bbdd_app.data.JuegoRepositoryImpl
import com.example.api_bbdd_app.data.local.AppDatabase
import com.example.api_bbdd_app.data.local.entities.JuegoEntity
import com.example.api_bbdd_app.model.Juego
import com.example.api_bbdd_app.model.toEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) :
    AndroidViewModel(application) { //Cambiamos view model por androidViewModel para tener acceso al contexto de la aplicación
    //conectar con el repositorio para mostrar la lista de juegos guardados
    private val database = AppDatabase.getInstance(application)

    private val dao = database.getJuegoDao()

    val repository = JuegoRepositoryImpl(dao)

    //Conectamos con el repositorio que extrae la lista de juegos de la bbdd gracias al dao
    val allJuegos: StateFlow<List<JuegoEntity>> = repository.getAllGames()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList<JuegoEntity>()
        )


    fun insertJuego(id: Long, nombre: String, genero: String, devId: Long) {
        viewModelScope.launch {
            val juego = Juego(id, nombre, genero, devId)
            val idG = repository.insertJuego(juego.toEntity())
        }
    }


}