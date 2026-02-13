package com.example.api_bbdd_app.ui.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_bbdd_app.data.remote.network.ApiRepository
import com.example.api_bbdd_app.model.Juego
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repositorio = ApiRepository()

    // Estado de la UI: Una lista vacía al empezar
    var juegos = mutableStateOf<List<Juego>>(emptyList())

    //Carga los juegos en la API nada mas iniciar la app (?)
    init {
        cargarJuegos()
    }

    private fun cargarJuegos() {
        viewModelScope.launch {
            val resultado = repositorio.getJuegos()
            juegos.value = resultado
        }
    }
}