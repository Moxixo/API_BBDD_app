package com.example.api_bbdd_app.ui.screens

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.api_bbdd_app.data.local.entities.JuegoCompleto
import com.example.api_bbdd_app.data.local.entities.JuegoEntity
import com.example.api_bbdd_app.data.local.entities.toModel
import com.example.api_bbdd_app.ui.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    onNavigateToGame: (Long?) -> Unit) {

    //Recogemos la lista de juegos del viewmodel que recibe del repository
    val juegos by viewModel.allJuegos.collectAsState()

    //variable que usamos para la barra de busqueda
    var searchText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Gestión De juegos", fontWeight = FontWeight.Bold) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToGame(null) },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar nuevo registro")
            }
        }
    ) { paddingValues ->

        // Columna principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            // 2. Placeholder (Campo de texto para buscar)
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Buscar juego...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = MaterialTheme.shapes.medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Lista de juegos
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 80.dp) // Espacio para que el FAB no tape el último elemento
            ) {
                items(juegos) { juego ->
                    JuegoItem(
                        juego = juego,
                        onDeleteClick = {
                            viewModel.eliminarJuegoBBDD(juego)
                            viewModel.eliminarJuegoApi(juego.juego.toModel())
                                        },
                        onEditClick = { onNavigateToGame(juego.juego.juego_id)}
                    )
                }
            }
        }
    }
}

@Composable
fun JuegoItem(
    juego: JuegoCompleto,
    onDeleteClick: () -> Unit,
    onEditClick:() -> Unit
) {
    //Creamos estado que expande la tarjeta de juego
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .animateContentSize(), // Esto hace que al abrirse lo haga con una animación suave
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        // Usamos una Column principal para apilar la fila superior y los detalles
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            // --- FILA SUPERIOR (Nombre y Botones) ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Nombre del juego
                Text(
                    text = juego.juego.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                // Botones a la derecha
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        // 2. Al pulsar, invertimos el estado (si era true pasa a false, etc.)
                        onClick = { expanded = !expanded },
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        // Cambiamos el texto según el estado de expanded
                        Text(if (expanded) "Ocultar" else "Details")
                    }
                    Button(
                        onClick = { onEditClick()},
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text("Edit")
                    }

                    Button(
                        onClick = { onDeleteClick() },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text("Delete")
                    }
                }
            }
            if (expanded) {
                // --- Sección Desplegable (Detalles, Dev, Plataformas) ---
                // Esta sección solo se renderiza si 'expanded' es true
                if (expanded) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Divider(color = MaterialTheme.colorScheme.outlineVariant) // Una línea separadora sutil
                    Spacer(modifier = Modifier.height(8.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        //Precio
                        DatoDetalle(titulo = "Precio", valor = "${juego.detalle?.precio} €")
                        // Género
                        DatoDetalle(titulo = "Género", valor = juego.juego.genero)
                        // Desarrollador (usando el nombre real gracias a la relación)
                        DatoDetalle(
                            titulo = "Desarrollador",
                            valor = juego.desarrollador?.nombre ?: "Desconocido"
                        )

                        // Plataformas (Lista)
                        if (juego.plataformas.isNotEmpty()) {
                            // Mapeamos la lista de objetos Plataforma a una sola cadena de texto separada por comas
                            val nombresPlataformas =
                                juego.plataformas.joinToString(", ") { it.nombre }
                            DatoDetalle(titulo = "Plataformas", valor = nombresPlataformas)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DatoDetalle(titulo: String, valor: String) {
    Row {
        Text(
            text = "$titulo: ",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = valor,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

