package com.example.api_bbdd_app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.api_bbdd_app.data.local.entities.DesarrolladorEntity
import com.example.api_bbdd_app.ui.viewmodel.GameViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    juegoId: Long?,
    viewModel: GameViewModel,
    onNavigateBack: () -> Unit, // Función para volver a la lista cuando se guarde
) {
    // estados de datos que vienen de la Base de Datos
    val desarrolladores: List<DesarrolladorEntity> by viewModel.desarrolladores.collectAsStateWithLifecycle()
    val plataformas by viewModel.plataformas.collectAsStateWithLifecycle()

    // estados para guardar lo que el usuario escribe en los campos de texto
    var nombre by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var requisitos by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }

    // estados para los selectores (Desplegable y Checkboxes)
    var expandedDev by remember { mutableStateOf(false) }
    var selectedDev by remember { mutableStateOf<DesarrolladorEntity?>(null) }

    // sin duplicados (setOf)
    var selectedPlataformas by remember { mutableStateOf(setOf<Long>()) }

    //Definimos LaunchedEffect para definir una corrutina
    // que ejecuta el código que tiene en segundo plano una sola vez al entrar en
    //la pantalla, evitando que se redibuje -> solo se redibuja cuando juegoId cambia
    LaunchedEffect(juegoId) {
        if (juegoId != null) { //si el id de los parametros, no es null
            // Llamamos a la función que creamos en el ViewModel de cargar datos de juego
            viewModel.cargarDatosDelJuego(juegoId) { juegoCompleto ->
                // Actualizamos los estados definidos arriba,
                //y se autorellenan los textFields
                nombre = juegoCompleto.juego.nombre
                genero = juegoCompleto.juego.genero
                descripcion = juegoCompleto.detalle.descripcion
                requisitos = juegoCompleto.detalle.requisitos
                precio = juegoCompleto.detalle.precio.toString()

                // Para los selectores:
                selectedDev = desarrolladores.find { dev ->
                    dev.desarrollador_id == juegoCompleto.juego.desarrollador_id
                }
                selectedPlataformas = juegoCompleto.plataformas.map { it.plataforma_id }.toSet()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Añadir Nuevo Juego") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { // Llama a la misma función de volver que el botón de guardar
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Cancelar y volver atrás"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        // Usamos Column con scroll por si la pantalla del móvil es pequeña
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // --- CAMPOS DEL JUEGO ENTITY---
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del Juego *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = genero,
                onValueChange = { genero = it },
                label = { Text("Género*") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // --- SELECTOR DE DESARROLLADOR (Dropdown) ---
            ExposedDropdownMenuBox(
                expanded = expandedDev,
                onExpandedChange = { expandedDev = !expandedDev }
            ) {
                OutlinedTextField(
                    // Mostramos el nombre del dev seleccionado o un texto por defecto
                    value = selectedDev?.nombre ?: "Selecciona un Desarrollador *",
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDev) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandedDev,
                    onDismissRequest = { expandedDev = false }
                ) {
                    desarrolladores.forEach { dev ->
                        DropdownMenuItem(
                            text = { Text(dev.nombre) },
                            onClick = {
                                selectedDev = dev
                                expandedDev = false
                            }
                        )
                    }
                }
            }

            // --- CAMPOS DEL DETALLE ENTITY ---
            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = requisitos,
                    onValueChange = { requisitos = it },
                    label = { Text("Requisitos") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )

                OutlinedTextField(
                    value = precio,
                    onValueChange = { precio = it },
                    label = { Text("Precio (€)") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            // --- SELECTOR DE PLATAFORMA ENTITY ---
            Text("Selecciona Plataformas:", style = MaterialTheme.typography.titleMedium)

            // Pintamos un Checkbox por cada plataforma que exista en la BBDD
            plataformas.forEach { plataforma ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = selectedPlataformas.contains(plataforma.plataforma_id),
                        onCheckedChange = { isChecked ->
                            // Si se marca, lo añadimos al Set. Si se desmarca, lo quitamos.
                            selectedPlataformas = if (isChecked) {
                                selectedPlataformas + plataforma.plataforma_id
                            } else {
                                selectedPlataformas - plataforma.plataforma_id
                            }
                        }
                    )
                    Text(text = plataforma.nombre)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- BOTÓN GUARDAR ---
            // Solo lo habilitamos si ha escrito nombre, género y elegido desarrollador
            val isFormValid = nombre.isNotBlank() && genero.isNotBlank() && selectedDev != null

            Button(
                onClick = {
                    // Convertimos el precio de String a Double (si está vacío o mal escrito, ponemos 0.0)
                    val precioDouble = precio.toDoubleOrNull() ?: 0.0

                    viewModel.guardarOActualizarNuevoJuego(
                        nombre = nombre,
                        genero = genero,
                        desarrolladorId = selectedDev!!.desarrollador_id, // Es seguro usar !! porque isFormValid lo comprueba
                        descripcion = descripcion,
                        requisitos = requisitos,
                        precio = precioDouble,
                        plataformasSeleccionadasIds = selectedPlataformas.toList(),
                        onSuccess = { onNavigateBack() } // Volvemos atrás cuando termine
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = isFormValid
            ) {
                Text("Guardar Juego")
            }
        }
    }
}