package com.example.api_bbdd_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.api_bbdd_app.ui.navegation.AppNavigation
import com.example.api_bbdd_app.ui.screens.HomeScreen
import com.example.api_bbdd_app.ui.viewmodel.GameViewModel
import com.example.api_bbdd_app.ui.viewmodel.HomeViewModel
import kotlin.getValue

class MainActivity : ComponentActivity() {
    private val homeViewModel: HomeViewModel by viewModels()
    private val gameViewModel: GameViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppNavigation(
                homeViewModel,
                gameViewModel
            )

        }

    }
}
