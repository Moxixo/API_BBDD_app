package com.example.api_bbdd_app.ui.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.api_bbdd_app.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel){

    val juegos = viewModel.juegos

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
                text = "GESTION DE VIDEOJUEGOS", modifier = Modifier.weight(2f),
                fontSize = 22.sp,
            )

        }



        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {

            items(juegos.value) { juego ->

                Row(
                    modifier = Modifier
                        .background(color = Color.Red, RoundedCornerShape(16.dp))
                        .fillMaxSize()
                        .height(80.dp)
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically) {

                    Text(juego.nombre)

                }
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {


            LargeFloatingActionButton(
                onClick = {},
                shape = CircleShape,
                modifier = Modifier.align(Alignment.BottomEnd),
                containerColor = Color.Red
            ) {
                Icon(Icons.Filled.Add, "Large floating action button")
            }
        }

    }
}