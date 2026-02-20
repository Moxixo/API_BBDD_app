package com.example.api_bbdd_app.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.api_bbdd_app.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {

    //Recogemos la lista de juegos del viewmodel que recibe del repository
    val juegos by viewModel.allJuegos.collectAsState()

    LazyColumn {
        items(juegos) { juego ->
            Text(juego.nombre + " - Género: " + juego.genero)
        }
    }


}