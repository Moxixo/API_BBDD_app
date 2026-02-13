package com.example.api_bbdd_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.api_bbdd_app.ui.screens.HomeScreen
import com.example.api_bbdd_app.ui.screens.HomeViewModel
import com.example.api_bbdd_app.ui.theme.Api_bbdd_appTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Api_bbdd_appTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val viewModel: HomeViewModel = viewModel()
                    val juegos by viewModel.juegos // Usa 'by' para que sea reactivo
                    val juego by viewModel.juego
                    HomeScreen(juegos = juegos, juego = juego)

                }
            }
        }
    }
}
