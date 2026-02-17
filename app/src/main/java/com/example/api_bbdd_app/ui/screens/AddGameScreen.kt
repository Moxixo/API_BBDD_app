package com.example.api_bbdd_app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AddGameScreen(){

    Column(modifier = Modifier
        .padding(15.dp)
        .fillMaxSize()){

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "AGREGAR UN VIDEOJUEGO", modifier = Modifier.weight(2f),
                fontSize = 22.sp,
            )
        }


        OutlinedTextField(
            value = "id",
            onValueChange = {  }, // Actualizamos el estado al escribir
            label = { Text("ID del videojuego") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))


        OutlinedTextField(
            value = "nombre",
            onValueChange = {},
            label = { Text("Nombre del videojuego") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(16.dp))


        OutlinedTextField(
            value = "genero",
            onValueChange = {  },
            label = { Text("Genero del videojuego") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = "1 - Nintenodo ...",
            onValueChange = {  },
            label = { Text("ID de la desarrolladora") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()){

            Button(
                onClick = {},
            ) {
                Text("Agregar API")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {},
            ) {
                Text("Agregar BBDD")
            }

        }

        Text("TEXTO DE CONFIRMACION TODO")



    }

}