package com.example.api_bbdd_app.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_bbdd_app.data.remote.network.ApiRepository
import com.example.api_bbdd_app.model.Juego
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    //Creamos el repositorio para utilizar los metodos de la API
    private val repositorio = ApiRepository()

    // Estado inicial: una lista vacía al empezar
    var juegos = mutableStateOf<List<Juego>>(emptyList())

    //Estado inicial: un objeto nulo
    var juego = mutableStateOf<Juego?>(null)

    //Carga los juegos en la API nada mas iniciar la app (?)
    init {
        cargarJuegos()
    }

    private fun cargarJuegos() {

        //Inicia una corrutina para cargar juegos
        viewModelScope.launch {

            //Cargame los juegos de la API
            val resultado = repositorio.getJuegos()
            //Settea la respuesta al valor de nuestra lista mutable
            juegos.value = resultado
        }
    }

    private fun cargarJuego(){

        //Inicia una corrutina para cargar un juego
        viewModelScope.launch {

            //Cargame este juego con id 3 de la API
            val resultado = repositorio.findJuego(3);

            //Si el resultado es diferente de null cargamelo en mi variable mutable
            if (resultado != null) {
                juego.value = resultado
            }
        }
    }
}