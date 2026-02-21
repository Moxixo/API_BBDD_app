package com.example.api_bbdd_app.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_bbdd_app.data.JuegoRepositoryImpl
import com.example.api_bbdd_app.data.local.AppDatabase
import com.example.api_bbdd_app.data.local.entities.JuegoCompleto
import com.example.api_bbdd_app.data.remote.network.ApiRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    //Cambiamos view model por androidViewModel para tener acceso al contexto de la aplicación
    //conectar con el repositorio para mostrar la lista de juegos guardados
    private val database = AppDatabase.getInstance(application)
    private val dao = database.getJuegoDao()

    private val api = ApiRepository()
    val repository = JuegoRepositoryImpl(dao,api)

    //carga atuomatica al iniciar homeScreen
    //que descarga los datos de la api
    init {
        viewModelScope.launch { repository.persistirJuegos() }
    }

    //Conectamos con el repositorio que extrae la lista de juegos de la bbdd gracias al dao
    //lectura de base de datos
    val allJuegos: StateFlow<List<JuegoCompleto>> = repository.getJuegosCompletos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    //conexión con repository -> dao -> bbdd -> eliminarJuego
    fun eliminarJuegoBBDD(juegoCompleto: JuegoCompleto){
        viewModelScope.launch {
            println("Juego eliminado.")
            repository.deleteJuego(juegoCompleto.juego)

        }
    }


}