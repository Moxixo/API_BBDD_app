package com.example.api_bbdd_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.api_bbdd_app.model.Juego

@Composable
fun HomeScreen(juegos : List<Juego>, juego : Juego?){



    
    LazyColumn(modifier = Modifier.padding(50.dp)) {

        items(juegos){ juego ->

            Row(modifier = Modifier.background(Color.Red).height(65.dp).padding(15.dp)) {

                Text(juego.nombre)
            }

        }

    }

    Row(modifier = Modifier.background(Color.Red).height(65.dp).padding(15.dp)) {

        Text(juego?.nombre ?: "no encontrao :(")
    }

}